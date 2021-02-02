package com.mercadolibre.productsearchapp.productdetail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.usecases.productdetail.ProductDetailInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel
import com.mercadolibre.productsearchapp.productsearch.SearchState
import com.mercadolibre.productsearchapp.productsearch.mapper.SearchProductUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailViewModelImpl(
    private val interactor: ProductDetailInteractor,
    private val mapper: ProductDetailUiMapper
) : ProductDetailViewModel() {
    private val mediator = MediatorLiveData<UiModel.ProductDetailResult>()

    override fun getProductDetails(productId: String) {
        mediator.value = UiModel.ProductDetailResult(ProductDetailState.LOADING)
        viewModelScope.launch(Dispatchers.Main) {
            mediator.value = withContext(Dispatchers.IO) {
                productId.takeIf { it.isNotBlank() }?.let { interactor.getProductDetails(productId) }
            }?.let { responseWrapper ->
                manageResponse(responseWrapper)
            } ?: UiModel.ProductDetailResult(ProductDetailState.ERROR)
        }
    }

    private fun manageResponse(responseWrapper: ResponseWrapper<Product>) =
        if (responseWrapper.hasError()) {
            UiModel.ProductDetailResult(ProductDetailState.ERROR)
        } else {
            responseWrapper.response?.let {
                UiModel.ProductDetailResult(
                    ProductDetailState.ERROR,
                    mapper.mapProductDetailToUiModel(it)
                )
            }?: UiModel.ProductDetailResult(ProductDetailState.ERROR)
        }

    override fun getMediator(): MediatorLiveData<UiModel.ProductDetailResult> = mediator
}

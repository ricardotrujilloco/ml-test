package com.mercadolibre.productsearchapp.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.usecases.productdetail.ProductDetailInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel
import kotlinx.coroutines.*

class ProductDetailViewModelImpl(
    private val interactor: ProductDetailInteractor,
    private val mapper: ProductDetailUiMapper,
    private val backgroundDispatcher: CoroutineDispatcher
) : ProductDetailViewModel() {
    private val mediator = MediatorLiveData<UiModel.ProductDetailResult>()

    override fun getProductDetails(productId: String) {
        mediator.value = UiModel.ProductDetailResult(ProductDetailState.LOADING)
        viewModelScope.launch(Dispatchers.Main) {
            mediator.value = withContext(backgroundDispatcher) {
                productId.takeIf { it.isNotBlank() }
                    ?.let { interactor.getProductDetails(productId) }
            }?.let { responseWrapper -> manageResponse(responseWrapper) }
                ?: UiModel.ProductDetailResult(ProductDetailState.ERROR)
        }
    }

    private fun manageResponse(responseWrapper: ResponseWrapper<Product>) =
        if (responseWrapper.hasError()) {
            UiModel.ProductDetailResult(ProductDetailState.ERROR)
        } else {
            responseWrapper.response?.let {
                UiModel.ProductDetailResult(
                    ProductDetailState.SUCCESS,
                    mapper.mapProductDetailToUiModel(it)
                )
            } ?: UiModel.ProductDetailResult(ProductDetailState.ERROR)
        }

    override fun getMediator(): LiveData<UiModel.ProductDetailResult> = mediator
}

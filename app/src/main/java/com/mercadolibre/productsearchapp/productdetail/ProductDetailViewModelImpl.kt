package com.mercadolibre.productsearchapp.productdetail

import androidx.lifecycle.MediatorLiveData
import com.mercadolibre.productsearch.usecases.productdetail.ProductDetailInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel

class ProductDetailViewModelImpl(
    private val interactor: ProductDetailInteractor
) : ProductDetailViewModel() {
    private val mediator = MediatorLiveData<UiModel.Product>()

    override fun getProductDetails(productId: String) {
        TODO("Not yet implemented")
    }
}

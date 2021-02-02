package com.mercadolibre.productsearchapp.productdetail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.productsearchapp.common.model.UiModel

abstract class ProductDetailViewModel : ViewModel() {
    abstract fun getProductDetails(productId: String)
    abstract fun getMediator(): MediatorLiveData<UiModel.ProductDetailResult>
}

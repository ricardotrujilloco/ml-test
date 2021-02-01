package com.mercadolibre.productsearchapp.productdetail

import androidx.lifecycle.ViewModel

abstract class ProductDetailViewModel: ViewModel() {
    abstract fun getProductDetails(productId: String)
}

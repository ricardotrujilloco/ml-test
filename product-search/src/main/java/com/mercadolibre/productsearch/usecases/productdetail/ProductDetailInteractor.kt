package com.mercadolibre.productsearch.usecases.productdetail

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper

interface ProductDetailInteractor {
    fun getProductDetails(productId: String): ResponseWrapper<Product>
}

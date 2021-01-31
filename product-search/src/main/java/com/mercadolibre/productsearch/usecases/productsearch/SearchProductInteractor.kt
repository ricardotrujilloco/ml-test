package com.mercadolibre.productsearch.usecases.productsearch

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper

interface SearchProductInteractor {
    fun searchProductByQuery(query: String): ResponseWrapper<List<Product>>
}

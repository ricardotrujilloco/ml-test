package com.mercadolibre.productsearch.usecases.api

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper

interface SearchProductByQueryInteractor {
    fun searchProductByQuery(query: String): ResponseWrapper<List<Product>>
}

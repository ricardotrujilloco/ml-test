package com.mercadolibre.productsearch.usecases.api

import com.mercadolibre.productsearch.entities.Product

interface SearchProductByQueryInteractor {
    fun searchProductByQuery(query: String): List<Product>
}

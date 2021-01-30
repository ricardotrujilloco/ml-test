package com.mercadolibre.productsearch.usecases

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import com.mercadolibre.productsearch.usecases.api.SearchProductByQueryInteractor

class SearchProductByQueryInteractorImpl(
    private val gateway: Repository<String, List<Product>>
) : SearchProductByQueryInteractor {

    override fun searchProductByQuery(query: String): ResponseWrapper<List<Product>> {
        return gateway.execute(query)
    }
}

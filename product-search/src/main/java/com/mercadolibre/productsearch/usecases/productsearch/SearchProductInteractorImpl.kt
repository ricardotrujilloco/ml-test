package com.mercadolibre.productsearch.usecases.productsearch

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository

class SearchProductInteractorImpl(
    private val gateway: Repository<String, List<Product>>
) : SearchProductInteractor {

    override fun searchProductByQuery(query: String): ResponseWrapper<List<Product>> {
        return gateway.execute(query)
    }
}

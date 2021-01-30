package com.mercadolibre.data.productsearch.mapper

import com.mercadolibre.data.productsearch.model.SearchProductBackendModel
import com.mercadolibre.productsearch.entities.Product

class BackendResponseMapperImpl : BackendResponseMapper<SearchProductBackendModel.SearchProductResponse, List<Product>> {

    override fun backendModelToEntity(response: SearchProductBackendModel.SearchProductResponse): List<Product> {
        return response.results.map {
            Product(it.id)
        }
    }
}

package com.mercadolibre.persistence.productsearch.mapper

import com.mercadolibre.persistence.BackendResponseMapper
import com.mercadolibre.persistence.productsearch.model.SearchProductBackendModel
import com.mercadolibre.productsearch.entities.Product

class ProductsSearchBackendResponseMapperImpl :
    BackendResponseMapper<SearchProductBackendModel.SearchProductResponse, List<Product>> {

    override fun backendModelToEntity(response: SearchProductBackendModel.SearchProductResponse): List<Product> {
        return response.results.map {
            Product(
                id = it.id,
                title = it.title
            )
        }
    }
}

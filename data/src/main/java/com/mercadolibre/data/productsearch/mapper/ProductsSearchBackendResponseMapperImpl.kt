package com.mercadolibre.data.productsearch.mapper

import com.mercadolibre.data.BackendResponseMapper
import com.mercadolibre.data.productsearch.model.SearchProductBackendModel
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

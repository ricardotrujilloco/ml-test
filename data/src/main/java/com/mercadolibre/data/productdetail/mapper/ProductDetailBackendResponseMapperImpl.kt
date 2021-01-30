package com.mercadolibre.data.productdetail.mapper

import com.mercadolibre.data.BackendResponseMapper
import com.mercadolibre.data.productdetail.model.ProductDetailsBackendModel
import com.mercadolibre.productsearch.entities.Product

class ProductDetailBackendResponseMapperImpl :
    BackendResponseMapper<ProductDetailsBackendModel.ProductDetailResponse, Product> {

    override fun backendModelToEntity(response: ProductDetailsBackendModel.ProductDetailResponse): Product {
        return Product(
            id = response.id,
            title = response.title
        )
    }
}

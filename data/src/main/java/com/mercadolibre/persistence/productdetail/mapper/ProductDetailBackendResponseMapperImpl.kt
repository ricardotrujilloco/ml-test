package com.mercadolibre.persistence.productdetail.mapper

import com.mercadolibre.persistence.common.BackendResponseMapper
import com.mercadolibre.persistence.productdetail.model.ProductDetailsBackendModel
import com.mercadolibre.productsearch.entities.Product

class ProductDetailBackendResponseMapperImpl :
    BackendResponseMapper<ProductDetailsBackendModel.ProductDetailResponse, Product> {

    override fun backendModelToEntity(response: ProductDetailsBackendModel.ProductDetailResponse): Product {
        return Product(
            id = response.id,
            title = response.title,
            price = response.price.toString(),
            images = response.pictures.map { it.url }
        )
    }
}

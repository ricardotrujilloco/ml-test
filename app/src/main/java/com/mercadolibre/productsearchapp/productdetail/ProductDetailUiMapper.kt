package com.mercadolibre.productsearchapp.productdetail

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearchapp.common.model.UiModel

interface ProductDetailUiMapper {
    fun mapProductDetailToUiModel(product: Product): UiModel.ProductDetail
}

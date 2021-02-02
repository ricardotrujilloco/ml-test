package com.mercadolibre.productsearchapp.productsearch.mapper

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearchapp.common.model.UiModel

interface SearchProductUiMapper {
    fun mapProductToUiModel(product: Product): UiModel.ProductResult
}

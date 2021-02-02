package com.mercadolibre.productsearchapp.productsearch.mapper

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearchapp.common.extensions.formatToLocale
import com.mercadolibre.productsearchapp.common.model.UiModel
import java.util.*

class SearchProductUiMapperImpl(private val locale: Locale) : SearchProductUiMapper {

    override fun mapProductToUiModel(product: Product): UiModel.ProductResult {
        return UiModel.ProductResult(
            id = product.id,
            title = product.title,
            price = product.price.formatToLocale(locale),
            thumbnail = product.thumbnail,
            freeShipping = product.freeShipping
        )
    }
}

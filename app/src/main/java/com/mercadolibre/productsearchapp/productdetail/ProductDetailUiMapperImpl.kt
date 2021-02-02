package com.mercadolibre.productsearchapp.productdetail

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearchapp.common.extensions.formatToLocale
import com.mercadolibre.productsearchapp.common.model.UiModel
import java.util.*

class ProductDetailUiMapperImpl(private val locale: Locale) : ProductDetailUiMapper {

    override fun mapProductDetailToUiModel(product: Product): UiModel.ProductDetail {
        return UiModel.ProductDetail(
            id = product.id,
            title = product.title,
            price = product.price.formatToLocale(locale),
            image = product.images.takeIf { it.isNotEmpty() }?.first() ?: "",
            freeShipping = product.freeShipping
        )
    }
}

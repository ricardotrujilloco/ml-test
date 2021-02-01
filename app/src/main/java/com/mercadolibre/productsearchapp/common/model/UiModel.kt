package com.mercadolibre.productsearchapp.common.model

import com.mercadolibre.productsearchapp.common.list.AdapterConstants
import com.mercadolibre.productsearchapp.common.list.ViewType

object UiModel {

    data class Product(
        val id: String,
        val title: String
    ) : ViewType {
        override fun getViewType() = AdapterConstants.PRODUCT
    }
}

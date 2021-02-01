package com.mercadolibre.productsearchapp.common.model

import com.mercadolibre.productsearchapp.common.list.AdapterConstants
import com.mercadolibre.productsearchapp.common.list.ViewType
import com.mercadolibre.productsearchapp.productsearch.SearchState

object UiModel {

    data class SearchResult(
        val state: SearchState,
        val products: List<Product>
    )

    data class Product(
        val id: String,
        val title: String,
        val price: String,
        val thumbnail: String,
        val freeShipping: Boolean
    ) : ViewType {
        override fun getViewType() = AdapterConstants.PRODUCT
    }
}

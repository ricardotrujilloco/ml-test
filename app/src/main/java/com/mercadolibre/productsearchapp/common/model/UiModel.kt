package com.mercadolibre.productsearchapp.common.model

import com.mercadolibre.productsearchapp.common.list.AdapterConstants
import com.mercadolibre.productsearchapp.common.list.ViewType
import com.mercadolibre.productsearchapp.productdetail.ProductDetailState
import com.mercadolibre.productsearchapp.productsearch.SearchState

object UiModel {

    data class SearchResult(
        val state: SearchState,
        val products: List<ProductResult>? = listOf()
    )

    data class ProductResult(
        val id: String = "",
        val title: String = "",
        val price: String = "",
        val thumbnail: String = "",
        val freeShipping: Boolean = false
    ) : ViewType {
        override fun getViewType() = AdapterConstants.PRODUCT
    }

    data class ProductDetailResult(
        val state: ProductDetailState,
        val product: ProductDetail? = ProductDetail()
    )

    data class ProductDetail(
        val id: String = "",
        val title: String = "",
        val price: String = "",
        val image: String = "",
        val freeShipping: Boolean = false,
        val warranty: String = "",
        val availableQuantity: String = ""
    )
}

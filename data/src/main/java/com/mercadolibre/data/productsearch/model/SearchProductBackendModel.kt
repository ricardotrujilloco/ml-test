package com.mercadolibre.data.productsearch.model

import com.google.gson.annotations.SerializedName

object SearchProductBackendModel {

    data class SearchProductResponse(
        @SerializedName("results") val results: List<Product> = emptyList()
    )

    data class Product(
        @SerializedName("id") val id: String = "",
        @SerializedName("title") val title: String = "",
        @SerializedName("price") val price: Double = 0.0,
        @SerializedName("currency_id") val currencyId: String = "",
        @SerializedName("thumbnail") val thumbnail: String = ""
    )

    data class BackEndError(
        @SerializedName("message") val message: String = "",
        @SerializedName("error") val error: String = "",
        @SerializedName("status") val status: Int = 0
    )
}

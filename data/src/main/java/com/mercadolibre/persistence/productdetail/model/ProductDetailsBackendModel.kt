package com.mercadolibre.persistence.productdetail.model

import com.google.gson.annotations.SerializedName

object ProductDetailsBackendModel {

    data class ProductDetailResponse(
        @SerializedName("id") val id: String = "",
        @SerializedName("title") val title: String = "",
        @SerializedName("available_quantity") val availableQuantity: Int = 0,
        @SerializedName("warranty") val warranty: String = "",
        @SerializedName("price") val price: Double = 0.0,
        @SerializedName("currency_id") val currencyId: String = "",
        @SerializedName("pictures") val pictures: List<ProductDetailImage> = listOf(),
        @SerializedName("condition") val condition: String = ""
    )

    data class ProductDetailImage(
        @SerializedName("url") val url: String = ""
    )
}

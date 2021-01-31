package com.mercadolibre.persistence.common

import com.google.gson.annotations.SerializedName

object BackendModel {

    data class BackEndError(
        @SerializedName("message") val message: String = "",
        @SerializedName("error") val error: String = "",
        @SerializedName("status") val status: Int = 0
    )
}

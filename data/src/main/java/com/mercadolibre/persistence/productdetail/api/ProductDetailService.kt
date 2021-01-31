package com.mercadolibre.persistence.productdetail.api

import com.mercadolibre.persistence.productdetail.model.ProductDetailsBackendModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailService {

    @GET("items/{productId}")
    fun getProductDetails(@Path("productId") productId: String?): Call<ProductDetailsBackendModel.ProductDetailResponse?>
}

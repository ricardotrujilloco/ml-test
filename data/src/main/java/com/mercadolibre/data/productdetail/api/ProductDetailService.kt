package com.mercadolibre.data.productdetail.api

import com.mercadolibre.data.productdetail.model.ProductDetailsBackendModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailService {

    @GET("items/{productId}")
    fun getProductDetails(@Path("productId") productId: String?): Call<ProductDetailsBackendModel.ProductDetailResponse?>
}

package com.mercadolibre.data.productsearch.api

import com.mercadolibre.data.productsearch.model.SearchProductBackendModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchProductsService {

    @GET("sites/MLA/search")
    fun searchProductsByQuery(@Query("q") query: String?): Call<SearchProductBackendModel.SearchProductResponse?>
}

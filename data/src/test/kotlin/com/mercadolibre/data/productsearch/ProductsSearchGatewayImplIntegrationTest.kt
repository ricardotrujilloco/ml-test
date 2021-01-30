package com.mercadolibre.data.productsearch

import com.google.gson.Gson
import com.mercadolibre.data.productsearch.api.SearchProductsService
import com.mercadolibre.data.productsearch.mapper.BackendResponseMapperImpl
import com.mercadolibre.data.productsearch.model.SearchProductBackendModel
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.ProductsSearchGateway
import interceptor.OfflineInterceptor
import interceptor.ResponseInterceptor
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsSearchGatewayImplIntegrationTest {
    private val errorSerializer = object : BackEndErrorSerializer {
        override fun fromJson(
            json: String
        ): SearchProductBackendModel.BackEndError {
            return Gson().fromJson(json, SearchProductBackendModel.BackEndError::class.java)
        }
    }
    private val productsMapper = BackendResponseMapperImpl()

    @Test
    fun `given a query string, when searchProductByQuery is called, then returns an instance of SearchProductResponse with some results`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: ProductsSearchGateway<List<Product>> =
            ProductsSearchGatewayImpl(service, errorSerializer, productsMapper)
        val query = "canon"

        val result = gateway.searchProductByQuery(query)

        assertTrue(result.response.isNotEmpty())
    }

    @Test
    fun `given an empty query string, when searchProductByQuery is called, then returns an instance of SearchProductResponse with empty results`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: ProductsSearchGateway<List<Product>> =
            ProductsSearchGatewayImpl(service, errorSerializer, productsMapper)
        val query = ""

        val result = gateway.searchProductByQuery(query)

        assertTrue(result.response.isEmpty())
    }

    @Test
    fun `given an invalid base URL, when searchProductByQuery is called, then returns an instance of UseCaseError`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre-invalid-base-url.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: ProductsSearchGateway<List<Product>> =
            ProductsSearchGatewayImpl(service, errorSerializer, productsMapper)
        val query = ""

        val result = gateway.searchProductByQuery(query)

        assertNotNull(result.error)
    }

    @Test
    fun `given a query string, when searchProductByQuery is called with error 400, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: ProductsSearchGateway<List<Product>> =
            ProductsSearchGatewayImpl(service, errorSerializer, productsMapper)
        val query = "product1"

        val result = gateway.searchProductByQuery(query)

        assertEquals("Some error message", result.error.message)
    }

    @Test
    fun `given a query string, when searchProductByQuery is called with error 500, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: ProductsSearchGateway<List<Product>> =
            ProductsSearchGatewayImpl(service, errorSerializer, productsMapper)
        val query = "product2"

        val result = gateway.searchProductByQuery(query)

        assertEquals("Some Backend error message", result.error.message)
    }

    @Test
    fun `given a query string, when searchProductByQuery is called with malformed response, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: ProductsSearchGateway<List<Product>> =
            ProductsSearchGatewayImpl(service, errorSerializer, productsMapper)
        val query = "product3"

        val result = gateway.searchProductByQuery(query)

        assertEquals("End of input at line 1 column 2 path \$.", result.error.message)
    }

    @Test
    fun `given a query string, when searchProductByQuery is called with null response, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: ProductsSearchGateway<List<Product>> =
            ProductsSearchGatewayImpl(service, errorSerializer, productsMapper)
        val query = "product4"

        val result = gateway.searchProductByQuery(query)

        assertNotNull(result.error)
        assertNull(result.error.message)
    }


    @Test
    fun `given a query string, when searchProductByQuery is called with NO NETWORK, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(OfflineInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: ProductsSearchGateway<List<Product>> =
            ProductsSearchGatewayImpl(service, errorSerializer, productsMapper)
        val query = "query"

        val result = gateway.searchProductByQuery(query)

        assertEquals("No internet", result.error.message)
    }
}

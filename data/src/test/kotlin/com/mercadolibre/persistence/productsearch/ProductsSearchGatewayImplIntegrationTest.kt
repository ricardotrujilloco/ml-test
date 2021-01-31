package com.mercadolibre.persistence.productsearch

import com.google.gson.Gson
import com.mercadolibre.persistence.common.serialization.BackEndErrorSerializer
import com.mercadolibre.persistence.common.model.BackendModel
import com.mercadolibre.persistence.productsearch.api.SearchProductsService
import com.mercadolibre.persistence.productsearch.mapper.ProductsSearchBackendResponseMapperImpl
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import interceptor.OfflineInterceptor
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsSearchGatewayImplIntegrationTest {
    private val errorSerializer = object :
        BackEndErrorSerializer {
        override fun fromJson(
            json: String
        ): BackendModel.BackEndError {
            return Gson().fromJson(json, BackendModel.BackEndError::class.java)
        }
    }
    private val productsMapper =
        ProductsSearchBackendResponseMapperImpl()

    @Test
    fun `given a query string, when execute is called, then returns a list of products`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: Repository<String, List<Product>> =
            ProductsSearchRepository(
                service,
                errorSerializer,
                productsMapper
            )
        val query = "canon"

        val result = gateway.execute(query)

        assertTrue(result.response!!.isNotEmpty())
    }

    @Test
    fun `given an empty query string, when execute is called, then returns an empty list`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: Repository<String, List<Product>> =
            ProductsSearchRepository(
                service,
                errorSerializer,
                productsMapper
            )
        val query = ""

        val result = gateway.execute(query)

        assertTrue(result.response!!.isEmpty())
    }

    @Test
    fun `given an invalid base URL, when execute is called, then returns an instance of UseCaseError`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre-invalid-base-url.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: Repository<String, List<Product>> =
            ProductsSearchRepository(
                service,
                errorSerializer,
                productsMapper
            )
        val query = ""

        val result = gateway.execute(query)

        assertNotNull(result.error)
    }

    @Test
    fun `given a query string, when execute is called with error 400, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ProductsSearchResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: Repository<String, List<Product>> =
            ProductsSearchRepository(
                service,
                errorSerializer,
                productsMapper
            )
        val query = "product1"

        val result = gateway.execute(query)

        assertEquals("Some error message", result.error!!.message)
    }

    @Test
    fun `given a query string, when execute is called with error 500, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ProductsSearchResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: Repository<String, List<Product>> =
            ProductsSearchRepository(
                service,
                errorSerializer,
                productsMapper
            )
        val query = "product2"

        val result = gateway.execute(query)

        assertEquals("Some Backend error message", result.error!!.message)
    }

    @Test
    fun `given a query string, when execute is called with malformed response, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ProductsSearchResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: Repository<String, List<Product>> =
            ProductsSearchRepository(
                service,
                errorSerializer,
                productsMapper
            )
        val query = "product3"

        val result = gateway.execute(query)

        assertEquals("End of input at line 1 column 2 path \$.", result.error!!.message)
    }

    @Test
    fun `given a query string, when execute is called with null response, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ProductsSearchResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: Repository<String, List<Product>> =
            ProductsSearchRepository(
                service,
                errorSerializer,
                productsMapper
            )
        val query = "product4"

        val result = gateway.execute(query)

        assertNotNull(result.error)
        assertNull(result.error!!.message)
    }


    @Test
    fun `given a query string, when execute is called with NO NETWORK, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(OfflineInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchProductsService::class.java)
        val gateway: Repository<String, List<Product>> =
            ProductsSearchRepository(
                service,
                errorSerializer,
                productsMapper
            )
        val query = "query"

        val result = gateway.execute(query)

        assertEquals("No internet", result.error!!.message)
    }
}

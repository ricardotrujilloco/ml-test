package com.mercadolibre.data.productdetail

import com.google.gson.Gson
import com.mercadolibre.data.BackEndErrorSerializer
import com.mercadolibre.data.common.BackendModel
import com.mercadolibre.data.productdetail.api.ProductDetailService
import com.mercadolibre.data.productdetail.cache.ProductDetailCache
import com.mercadolibre.data.productdetail.mapper.ProductDetailBackendResponseMapperImpl
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import interceptor.OfflineInterceptor
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsDetailGatewayImplIntegrationTest {
    private val errorSerializer = object :
        BackEndErrorSerializer {
        override fun fromJson(
            json: String
        ): BackendModel.BackEndError {
            return Gson().fromJson(json, BackendModel.BackEndError::class.java)
        }
    }
    private val productsMapper = ProductDetailBackendResponseMapperImpl()

    @Test
    fun `given a valid product ID, when execute is called, then returns a Product with a valid id and title`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "MLA885869161"

        val result = gateway.execute(productId)

        assertTrue(result.response!!.id.isNotBlank())
        assertTrue(result.response!!.title.isNotBlank())
    }

    @Test
    fun `given an invalid product ID, when execute is called, then returns an use case error`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "invalid-id"
        `when`(cache.getProductDetails()).thenReturn(Product())

        val result = gateway.execute(productId)

        assertNull(result.response)
        assertNotNull(result.error)
    }

    @Test
    fun `given an empty product ID, when execute is called and no cached Product, then returns an use case error`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = ""
        `when`(cache.getProductDetails()).thenReturn(Product())

        val result = gateway.execute(productId)

        assertNull(result.response)
        assertNotNull(result.error)
    }

    @Test
    fun `given an invalid base URL, when execute is called and a cached Product, then returns the expected product`() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre-invalid-base-url.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "MLA885869161"
        `when`(cache.getProductDetails()).thenReturn(Product(id = productId))

        val result = gateway.execute(productId)

        assertEquals(productId, result.response!!.id)
        assertNull(result.error)
    }

    @Test
    fun `given a product ID, when execute is called with error 400 and a cached Product, then returns the expected product`() {
        val okHttpClientBuilder =
            OkHttpClient.Builder().addInterceptor(ProductDetailResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "product1"
        `when`(cache.getProductDetails()).thenReturn(Product(id = productId))

        val result = gateway.execute(productId)

        assertEquals(productId, result.response!!.id)
        assertNull(result.error)
    }

    @Test
    fun `given a query string, when execute is called with error 500 and no cached Product, then returns the expected error message`() {
        val okHttpClientBuilder =
            OkHttpClient.Builder().addInterceptor(ProductDetailResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "product2"
        `when`(cache.getProductDetails()).thenReturn(Product())

        val result = gateway.execute(productId)

        assertEquals("Some Backend error message", result.error!!.message)
    }

    @Test
    fun `given a query string, when execute is called with malformed response and no cached Product, then returns the expected error message`() {
        val okHttpClientBuilder =
            OkHttpClient.Builder().addInterceptor(ProductDetailResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "product3"
        `when`(cache.getProductDetails()).thenReturn(Product())

        val result = gateway.execute(productId)

        assertEquals("End of input at line 1 column 2 path \$.", result.error!!.message)
    }

    @Test
    fun `given a query string, when execute is called with null response and no cached Product, then returns the expected error message`() {
        val okHttpClientBuilder =
            OkHttpClient.Builder().addInterceptor(ProductDetailResponseInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "product4"
        `when`(cache.getProductDetails()).thenReturn(Product())

        val result = gateway.execute(productId)

        assertNotNull(result.error)
        assertNull(result.error!!.message)
    }


    @Test
    fun `given a query string, when execute is called with NO NETWORK and no cached Product, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(OfflineInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "query"
        `when`(cache.getProductDetails()).thenReturn(Product())

        val result = gateway.execute(productId)

        assertEquals("No internet", result.error!!.message)
    }

    @Test
    fun `given a query string, when execute is called with NO NETWORK and a cached Product, then returns the expected error message`() {
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(OfflineInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductDetailService::class.java)
        val cache = mock(ProductDetailCache::class.java)
        val gateway: Repository<String, Product> =
            ProductDetailRepository(service, cache, errorSerializer, productsMapper)
        val productId = "query"
        `when`(cache.getProductDetails()).thenReturn(Product(id = productId))

        val result = gateway.execute(productId)

        assertEquals(productId, result.response!!.id)
        assertNull(result.error)
    }
}

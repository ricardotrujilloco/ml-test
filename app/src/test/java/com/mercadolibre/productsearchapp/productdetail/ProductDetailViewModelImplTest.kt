package com.mercadolibre.productsearchapp.productdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.usecases.UseCaseError
import com.mercadolibre.productsearch.usecases.productdetail.ProductDetailInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ProductDetailViewModelImplTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given a productId, when gets product details, then returns the expected product`() = runBlocking {
            val interactor = mock(ProductDetailInteractor::class.java)
            val mapper = mock(ProductDetailUiMapper::class.java)
            val viewModel: ProductDetailViewModel = ProductDetailViewModelImpl(interactor, mapper, Dispatchers.Main)
            val expectedProductId = "productId"
            val product = Product(id = expectedProductId)
            val productDetail = UiModel.ProductDetail(id = expectedProductId)
            val response = ResponseWrapper(product)
            `when`(interactor.getProductDetails(expectedProductId)).thenReturn(response)
            `when`(mapper.mapProductDetailToUiModel(product)).thenReturn(productDetail)

            viewModel.getProductDetails(expectedProductId)

            viewModel.getMediator().observeForever {
                assertEquals(expectedProductId, it?.product?.id)
            }
        }

    @Test
    fun `given a productId, when gets product details with error, then returns the expected error`() = runBlocking {
        val interactor = mock(ProductDetailInteractor::class.java)
        val mapper = mock(ProductDetailUiMapper::class.java)
        val viewModel: ProductDetailViewModel = ProductDetailViewModelImpl(interactor, mapper, Dispatchers.Main)
        val expectedProductId = "productId"
        val product = Product(id = expectedProductId)
        val productDetail = UiModel.ProductDetail()
        val error = UseCaseError(error = "error", message = "message")
        val response = ResponseWrapper(product, error)
        `when`(interactor.getProductDetails(expectedProductId)).thenReturn(response)
        `when`(mapper.mapProductDetailToUiModel(product)).thenReturn(productDetail)

        viewModel.getProductDetails(expectedProductId)

        viewModel.getMediator().observeForever {
            assertTrue(it?.product?.id?.isEmpty()!!)
        }
    }

    @Test
    fun `given a productId, when gets product details with no error and null product, then returns and empty product`() = runBlocking {
        val interactor = mock(ProductDetailInteractor::class.java)
        val mapper = mock(ProductDetailUiMapper::class.java)
        val viewModel: ProductDetailViewModel = ProductDetailViewModelImpl(interactor, mapper, Dispatchers.Main)
        val expectedProductId = "productId"
        val product = Product(id = expectedProductId)
        val productDetail = UiModel.ProductDetail()
        val error = UseCaseError()
        val response = ResponseWrapper<Product>(error = error)
        `when`(interactor.getProductDetails(expectedProductId)).thenReturn(response)
        `when`(mapper.mapProductDetailToUiModel(product)).thenReturn(productDetail)

        viewModel.getProductDetails(expectedProductId)

        viewModel.getMediator().observeForever {
            assertTrue(it?.product?.id?.isEmpty()!!)
        }
    }
}

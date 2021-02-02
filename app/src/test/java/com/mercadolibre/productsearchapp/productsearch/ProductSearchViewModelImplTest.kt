package com.mercadolibre.productsearchapp.productsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.usecases.UseCaseError
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel
import com.mercadolibre.productsearchapp.productsearch.mapper.SearchProductUiMapper
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ProductSearchViewModelImplTest {

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
    fun `given a productId, when search for products, then returns the expected product`() =
        runBlocking {
            val interactor = mock(SearchProductInteractor::class.java)
            val mapper = mock(SearchProductUiMapper::class.java)
            val viewModel: ProductSearchViewModel =
                ProductSearchViewModelImpl(interactor, mapper, Dispatchers.Main)
            val query = "query"
            val productId = "productId"
            val products = listOf(Product(id = "id"))
            val productDetail = UiModel.ProductResult(id = productId)
            val response = ResponseWrapper(products)
            `when`(interactor.searchProductByQuery(query)).thenReturn(response)
            `when`(mapper.mapProductToUiModel(products.first())).thenReturn(productDetail)

            viewModel.searchProducts(query)

            viewModel.getMediator().observeForever {
                assertEquals(productId, it?.products?.first()?.id)
            }
        }

    @Test
    fun `given a productId, when search for products with error, then returns the expected error`() =
        runBlocking {
            val interactor = mock(SearchProductInteractor::class.java)
            val mapper = mock(SearchProductUiMapper::class.java)
            val viewModel: ProductSearchViewModel =
                ProductSearchViewModelImpl(interactor, mapper, Dispatchers.Main)
            val query = "query"
            val products = listOf(Product(id = "id"))
            val error = UseCaseError(error = "error", message = "message")
            val response = ResponseWrapper(products, error)
            `when`(interactor.searchProductByQuery(query)).thenReturn(response)

            viewModel.searchProducts(query)

            viewModel.getMediator().observeForever {
                assertTrue(it?.state == SearchState.ERROR)
            }
        }

    @Test
    fun `given a productId, when gets product details with no error and null product, then returns and empty product`() =
        runBlocking {
            val interactor = mock(SearchProductInteractor::class.java)
            val mapper = mock(SearchProductUiMapper::class.java)
            val viewModel: ProductSearchViewModel =
                ProductSearchViewModelImpl(interactor, mapper, Dispatchers.Main)
            val expectedProductId = "productId"
            val response = ResponseWrapper<List<Product>>()
            `when`(interactor.searchProductByQuery(expectedProductId)).thenReturn(response)

            viewModel.searchProducts(expectedProductId)

            viewModel.getMediator().observeForever {
                assertTrue(it?.state == SearchState.NO_RESULTS)
                assertTrue(it?.products?.isEmpty()!!)
            }
        }
}

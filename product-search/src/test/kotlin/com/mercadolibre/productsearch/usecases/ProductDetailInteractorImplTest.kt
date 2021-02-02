package com.mercadolibre.productsearch.usecases

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import com.mercadolibre.productsearch.usecases.productdetail.ProductDetailInteractor
import com.mercadolibre.productsearch.usecases.productdetail.ProductDetailInteractorImpl
import org.junit.Test
import org.mockito.Mockito.*

class ProductDetailInteractorImplTest {

    @Test
    fun `given a product ID, when execute is called, then it calls getProductDetails in gateway`() {
        val gateway = mock(Repository::class.java) as Repository<String, Product>
        val interactor: ProductDetailInteractor = ProductDetailInteractorImpl(gateway)
        val productId = "productId"
        val response = ResponseWrapper(Product(), UseCaseError())
        `when`(gateway.execute(productId)).thenReturn(response)

        interactor.getProductDetails(productId)

        verify(gateway).execute(productId)
    }
}

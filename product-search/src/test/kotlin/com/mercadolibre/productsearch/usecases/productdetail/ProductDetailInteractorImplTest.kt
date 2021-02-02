package com.mercadolibre.productsearch.usecases.productdetail

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ProductDetailInteractorImplTest {

    @Test
    fun `given a product ID, when getting product details, calls execute in repository`() {
        val repository = mock(Repository::class.java) as Repository<String, Product>
        val interactor = ProductDetailInteractorImpl(repository)
        val productId = "productId"

        interactor.getProductDetails(productId)

        verify(repository).execute(productId)
    }
}

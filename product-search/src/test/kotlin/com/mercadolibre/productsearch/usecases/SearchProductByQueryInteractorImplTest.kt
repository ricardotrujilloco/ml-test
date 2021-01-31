package com.mercadolibre.productsearch.usecases

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractorImpl
import org.junit.Test
import org.mockito.Mockito.*

class SearchProductByQueryInteractorImplTest {

    @Test
    fun `given a query string, when execute is called, then it calls searchProductByQuery in gateway`() {
       val gateway = mock(Repository::class.java) as Repository<String, List<Product>>
        val interactor: SearchProductInteractor = SearchProductInteractorImpl(gateway)
        val query = "query"
        val response = ResponseWrapper<List<Product>>(listOf(), UseCaseError())
        `when`(gateway.execute(query)).thenReturn(response)

        interactor.searchProductByQuery(query)

        verify(gateway).execute(query)
    }
}

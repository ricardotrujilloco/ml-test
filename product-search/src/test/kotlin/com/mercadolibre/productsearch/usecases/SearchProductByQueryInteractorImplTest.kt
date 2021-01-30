package com.mercadolibre.productsearch.usecases

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import com.mercadolibre.productsearch.usecases.api.SearchProductByQueryInteractor
import org.junit.Test
import org.mockito.Mockito.*

class SearchProductByQueryInteractorImplTest {

    @Test
    fun `given a query string, when execute is called, then it calls searchProductByQuery in gateway`() {
       val gateway = mock(Repository::class.java) as Repository<String, List<Product>>
        val interactor: SearchProductByQueryInteractor = SearchProductByQueryInteractorImpl(gateway)
        val query = "query"
        val response = ResponseWrapper<List<Product>>(listOf(), UseCaseError())
        `when`(gateway.execute(query)).thenReturn(response)

        interactor.searchProductByQuery(query)

        verify(gateway).execute(query)
    }
}

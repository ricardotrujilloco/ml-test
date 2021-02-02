package com.mercadolibre.productsearch.usecases.productsearch

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class SearchProductInteractorImplTest {

    @Test
    fun `given a product ID, when getting searching for products, calls execute in repository`() {
        val repository = mock(Repository::class.java) as Repository<String, List<Product>>
        val interactor = SearchProductInteractorImpl(repository)
        val query = "query"

        interactor.searchProductByQuery(query)

        Mockito.verify(repository).execute(query)
    }
}

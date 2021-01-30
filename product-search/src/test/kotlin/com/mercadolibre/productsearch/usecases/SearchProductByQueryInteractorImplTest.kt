package com.mercadolibre.productsearch.usecases

import com.mercadolibre.productsearch.usecases.api.SearchProductByQueryInteractor
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class SearchProductByQueryInteractorImplTest {

    @Test
    fun `given a query string, when searchProductByQuery is called, then it returns a list of products`() {
        val interactor: SearchProductByQueryInteractor = SearchProductByQueryInteractorImpl()
        val query = "query"

        val result = interactor.searchProductByQuery(query)

        assertNotNull(result)
    }
}

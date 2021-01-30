package com.mercadolibre.productsearch.usecases

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.ProductsSearchGateway
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.ProductsSearchResponseMapper
import com.mercadolibre.productsearch.usecases.api.SearchProductByQueryInteractor
import org.junit.Test
import org.mockito.Mockito.*

class SearchProductByQueryInteractorImplTest {

    @Test
    fun `given a query string, when searchProductByQuery is called, then it calls searchProductByQuery in gateway`() {
       val gateway = mock(ProductsSearchGateway::class.java) as ProductsSearchGateway<List<Product>>
        val mapper = mock(ProductsSearchResponseMapper::class.java) as ProductsSearchResponseMapper<List<Product>>
        val interactor: SearchProductByQueryInteractor = SearchProductByQueryInteractorImpl(gateway, mapper)
        val query = "query"
        val response = ResponseWrapper<List<Product>>(listOf(), UseCaseError())
        `when`(gateway.searchProductByQuery(query)).thenReturn(response)

        interactor.searchProductByQuery(query)

        verify(gateway).searchProductByQuery(query)
    }

    @Test
    fun `given a query string, when searchProductByQuery is called, then it calls mapFromGatewayResponseToResponseModel in mapper`() {
        val gateway = mock(ProductsSearchGateway::class.java) as ProductsSearchGateway<List<Product>>
        val mapper = mock(ProductsSearchResponseMapper::class.java) as ProductsSearchResponseMapper<List<Product>>
        val interactor: SearchProductByQueryInteractor = SearchProductByQueryInteractorImpl(gateway, mapper)
        val query = "query"
        val response = ResponseWrapper<List<Product>>(listOf(), UseCaseError())
        `when`(gateway.searchProductByQuery(query)).thenReturn(response)

        interactor.searchProductByQuery(query)

        verify(mapper).mapFromGatewayResponseToResponseModel(response.response)
    }
}

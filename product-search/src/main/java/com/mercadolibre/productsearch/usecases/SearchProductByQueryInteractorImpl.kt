package com.mercadolibre.productsearch.usecases

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.ProductsSearchGateway
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.ProductsSearchResponseMapper
import com.mercadolibre.productsearch.usecases.api.SearchProductByQueryInteractor

class SearchProductByQueryInteractorImpl<ENTITY>(
    private val gateway: ProductsSearchGateway<ENTITY>,
    private val gatewayResponseMapper: ProductsSearchResponseMapper<ENTITY>
) : SearchProductByQueryInteractor {

    override fun searchProductByQuery(query: String): ResponseWrapper<List<Product>> {
        val gatewayResponse: ResponseWrapper<ENTITY> = gateway.searchProductByQuery(query)
        val responseModel = gatewayResponseMapper.mapFromGatewayResponseToResponseModel(gatewayResponse.response)
        return ResponseWrapper(responseModel, UseCaseError())
    }
}

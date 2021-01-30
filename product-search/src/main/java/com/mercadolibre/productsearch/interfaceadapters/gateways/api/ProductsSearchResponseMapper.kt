package com.mercadolibre.productsearch.interfaceadapters.gateways.api

import com.mercadolibre.productsearch.entities.Product

interface ProductsSearchResponseMapper<GATEWAY_TYPE> {
    fun mapFromGatewayResponseToResponseModel(gatewayResponse: GATEWAY_TYPE): List<Product>
}

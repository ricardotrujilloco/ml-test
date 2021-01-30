package com.mercadolibre.productsearch.interfaceadapters.gateways.api

import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper

interface ProductsSearchGateway<GATEWAY_TYPE> {
    fun searchProductByQuery(query: String): ResponseWrapper<GATEWAY_TYPE>
}

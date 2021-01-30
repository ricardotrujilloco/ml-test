package com.mercadolibre.productsearch.interfaceadapters.gateways.api

import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper

interface Repository<ARGUMENTS, GATEWAY_TYPE> {
    fun execute(arguments: ARGUMENTS?): ResponseWrapper<GATEWAY_TYPE>
}

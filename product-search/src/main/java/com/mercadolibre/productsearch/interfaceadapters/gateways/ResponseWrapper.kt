package com.mercadolibre.productsearch.interfaceadapters.gateways

import com.mercadolibre.productsearch.usecases.UseCaseError

data class ResponseWrapper<ENTITY_TYPE>(
    val response: ENTITY_TYPE? = null,
    val error: UseCaseError? = null
) {
    fun hasError(): Boolean =
        error?.error?.isNotEmpty() == true || error?.message?.isNotEmpty() == true
}

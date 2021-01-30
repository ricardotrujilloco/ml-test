package com.mercadolibre.data.productsearch.mapper

interface BackendResponseMapper<BACKEND_TYPE, ENTITY> {
    fun backendModelToEntity(response: BACKEND_TYPE): ENTITY
}

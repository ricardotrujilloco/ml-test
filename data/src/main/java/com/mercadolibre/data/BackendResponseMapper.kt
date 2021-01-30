package com.mercadolibre.data

interface BackendResponseMapper<BACKEND_TYPE, ENTITY> {
    fun backendModelToEntity(response: BACKEND_TYPE): ENTITY
}

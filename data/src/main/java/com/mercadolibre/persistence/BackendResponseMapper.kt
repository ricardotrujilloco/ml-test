package com.mercadolibre.persistence

interface BackendResponseMapper<BACKEND_TYPE, ENTITY> {
    fun backendModelToEntity(response: BACKEND_TYPE): ENTITY
}

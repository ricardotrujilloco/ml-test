package com.mercadolibre.persistence.common

interface BackendResponseMapper<BACKEND_TYPE, ENTITY> {
    fun backendModelToEntity(response: BACKEND_TYPE): ENTITY
}

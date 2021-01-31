package com.mercadolibre.persistence.common.serialization

import com.mercadolibre.persistence.common.model.BackendModel

interface BackEndErrorSerializer {
    fun fromJson(json: String): BackendModel.BackEndError
}

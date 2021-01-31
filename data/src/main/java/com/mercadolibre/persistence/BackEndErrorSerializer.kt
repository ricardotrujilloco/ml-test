package com.mercadolibre.persistence

import com.mercadolibre.persistence.common.BackendModel

interface BackEndErrorSerializer {
    fun fromJson(json: String): BackendModel.BackEndError
}

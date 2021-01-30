package com.mercadolibre.data

import com.mercadolibre.data.common.BackendModel

interface BackEndErrorSerializer {
    fun fromJson(json: String): BackendModel.BackEndError
}

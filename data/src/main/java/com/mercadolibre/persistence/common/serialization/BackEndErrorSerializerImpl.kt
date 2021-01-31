package com.mercadolibre.persistence.common.serialization

import com.google.gson.Gson
import com.mercadolibre.persistence.common.model.BackendModel

class BackEndErrorSerializerImpl(private val gson: Gson) : BackEndErrorSerializer {

    override fun fromJson(json: String): BackendModel.BackEndError {
        return gson.fromJson(json, BackendModel.BackEndError::class.java)
    }
}

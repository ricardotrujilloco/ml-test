package com.mercadolibre.data.productsearch

import com.mercadolibre.data.productsearch.model.SearchProductBackendModel

interface BackEndErrorSerializer {
    fun fromJson(json: String): SearchProductBackendModel.BackEndError
}

package com.mercadolibre.persistence.common.log

interface ErrorLogger {
    fun log(origin: String, e: Exception)
}

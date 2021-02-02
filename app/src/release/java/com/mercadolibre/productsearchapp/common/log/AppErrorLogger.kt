package com.mercadolibre.productsearchapp.common.log

import android.util.Log
import com.mercadolibre.persistence.common.log.ErrorLogger

class AppErrorLogger : ErrorLogger {
    private val tag = "ML-Release"

    override fun log(origin: String, e: Exception) {
        Log.e(tag, origin + " -> " + e.message)
    }
}

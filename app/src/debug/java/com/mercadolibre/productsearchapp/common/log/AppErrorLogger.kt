package com.mercadolibre.productsearchapp.common.log

import android.util.Log
import com.mercadolibre.persistence.common.log.ErrorLogger

class AppErrorLogger : ErrorLogger {
    private val tag = "ML-Debug"

    override fun log(origin: String, e: Exception) {
        Log.e(tag, "$origin<------ BEGINNING OF LOG ------>")
        Log.e(tag, origin + " -> " + e.message)
        Log.e(tag, origin + " -> " + e.cause)
        Log.e(tag, origin + " -> " + e.stackTrace)
        Log.e(tag, "$origin<------ END OF LOG ------>")
    }
}

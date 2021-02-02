package com.mercadolibre.productsearchapp.common.extensions

import java.text.NumberFormat
import java.util.*

fun String.formatToLocale(locale: Locale): String {
    return takeIf { it.isNotBlank() }?.let {
        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        numberFormat.maximumFractionDigits = 0
        numberFormat.format(toDouble())
    } ?: ""
}

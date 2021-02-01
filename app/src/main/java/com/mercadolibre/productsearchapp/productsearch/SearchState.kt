package com.mercadolibre.productsearchapp.productsearch

enum class SearchState(private val code: Int) {
    SUCCESS(0),
    LOADING(1),
    ERROR(2),
    NO_RESULTS(3);

    fun code() = this.code
}

package com.mercadolibre.productsearchapp.productdetail

enum class ProductDetailState(private val code: Int) {
    SUCCESS(0),
    LOADING(1),
    ERROR(2);

    fun code() = this.code
}

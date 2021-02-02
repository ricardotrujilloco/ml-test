package com.mercadolibre.productsearch.entities

data class Product(
    val id: String = "",
    val title: String = "",
    val price: String = "",
    val thumbnail: String = "",
    val freeShipping: Boolean = false,
    val images: List<String> = listOf()
)

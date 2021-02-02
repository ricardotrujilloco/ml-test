package com.mercadolibre.productsearch.entities

import org.junit.Test

class ProductTest {

    @Test
    fun `Product entity contract test`() {
        val product = Product(
            id = "id",
            title = "title",
            price = "12.0",
            thumbnail = "thumbnail",
            freeShipping = true,
            images = listOf("image"),
            warranty = "warranty",
            availableQuantity = 12,
        )
        product.id
        product.title
        product.price
        product.thumbnail
        product.freeShipping
        product.images
        product.warranty
        product.availableQuantity
    }
}

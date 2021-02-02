package com.mercadolibre.productsearchapp.productsearch.mapper

import com.mercadolibre.productsearch.entities.Product
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class SearchProductUiMapperImplTest {

    @Test
    fun `given a product model, then returns the ui model with the expected values`() {
        val mapper: SearchProductUiMapper = SearchProductUiMapperImpl(Locale("es", "AR"))
        val product = Product(
            id = "id",
            title = "title",
            price = "12.0",
            thumbnail = "thumbnail",
            freeShipping = true,
            images = listOf("image"),
            warranty = "warranty",
            availableQuantity = 5
        )

        val uiModel = mapper.mapProductToUiModel(product)

        assertEquals(product.id, uiModel.id)
        assertEquals(product.title, uiModel.title)
        assertEquals("$12", uiModel.price)
        assertEquals(product.thumbnail, uiModel.thumbnail)
        assertEquals(product.freeShipping, uiModel.freeShipping)
    }
}

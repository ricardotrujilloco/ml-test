package com.mercadolibre.productsearchapp.productdetail

import com.mercadolibre.productsearch.entities.Product
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ProductDetailUiMapperImplTest {

    @Test
    fun `given a product model, then returns the ui model with the expected id`() {
        val mapper: ProductDetailUiMapper = ProductDetailUiMapperImpl(Locale("es", "AR"))
        val product = Product(
            id = "id"
        )

        val uiModel = mapper.mapProductDetailToUiModel(product)

        assertEquals(product.id, uiModel.id)
    }

    @Test
    fun `given a product model, then returns the ui model with the expected title`() {
        val mapper: ProductDetailUiMapper = ProductDetailUiMapperImpl(Locale("es", "AR"))
        val product = Product(
            title = "title"
        )

        val uiModel = mapper.mapProductDetailToUiModel(product)

        assertEquals(product.title, uiModel.title)
    }

    @Test
    fun `given a product model, then returns the ui model with the expected price`() {
        val mapper: ProductDetailUiMapper = ProductDetailUiMapperImpl(Locale("es", "AR"))
        val product = Product(
            price = "12.0"
        )

        val uiModel = mapper.mapProductDetailToUiModel(product)

        assertEquals("$12", uiModel.price)
    }

    @Test
    fun `given a product model, then returns the ui model with the expected image`() {
        val mapper: ProductDetailUiMapper = ProductDetailUiMapperImpl(Locale("es", "AR"))
        val product = Product(
            images = listOf("image")
        )

        val uiModel = mapper.mapProductDetailToUiModel(product)

        assertEquals(product.images.first(), uiModel.image)
    }

    @Test
    fun `given a product model, then returns the ui model with the expected freeShipping`() {
        val mapper: ProductDetailUiMapper = ProductDetailUiMapperImpl(Locale("es", "AR"))
        val product = Product(
            freeShipping = true
        )

        val uiModel = mapper.mapProductDetailToUiModel(product)

        assertEquals(product.freeShipping, uiModel.freeShipping)
    }

    @Test
    fun `given a product model, then returns the ui model with the expected warranty`() {
        val mapper: ProductDetailUiMapper = ProductDetailUiMapperImpl(Locale("es", "AR"))
        val product = Product(
            warranty = "warranty"
        )

        val uiModel = mapper.mapProductDetailToUiModel(product)

        assertEquals(product.warranty, uiModel.warranty)
    }

    @Test
    fun `given a product model, then returns the ui model with the expected availableQuantity`() {
        val mapper: ProductDetailUiMapper = ProductDetailUiMapperImpl(Locale("es", "AR"))
        val product = Product(
            availableQuantity = 3
        )

        val uiModel = mapper.mapProductDetailToUiModel(product)

        assertEquals(product.availableQuantity.toString(), uiModel.availableQuantity)
    }
}

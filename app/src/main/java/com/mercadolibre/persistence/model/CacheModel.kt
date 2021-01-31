package com.mercadolibre.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mercadolibre.persistence.AppDatabase.Companion.PRODUCT_TABLE
import com.mercadolibre.persistence.converter.ProductDetailImageConverter

object CacheModel {

    @Entity(tableName = PRODUCT_TABLE)
    data class ProductDetailResponse(
        @PrimaryKey
        val id: String = "",
        val title: String = "",
        val price: Double = 0.0,
        val currencyId: String = "",
        @TypeConverters(ProductDetailImageConverter::class)
        val pictures: List<ProductDetailImage> = listOf(),
        val condition: String = ""
    )

    data class ProductDetailImage(
        val title: String = ""
    )
}

package com.mercadolibre.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mercadolibre.persistence.converter.ProductDetailImageConverter
import com.mercadolibre.persistence.dao.ProductDao
import com.mercadolibre.persistence.model.CacheModel

@Database(
    entities = [
        CacheModel.ProductDetailResponse::class
    ], version = 1, exportSchema = false
)
@TypeConverters(
    ProductDetailImageConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        const val PRODUCT_TABLE = "PRODUCT_TABLE"
    }
}

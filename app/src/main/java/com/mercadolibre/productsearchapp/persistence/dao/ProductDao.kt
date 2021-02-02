package com.mercadolibre.productsearchapp.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mercadolibre.productsearchapp.persistence.AppDatabase.Companion.PRODUCT_TABLE
import com.mercadolibre.productsearchapp.persistence.model.CacheModel

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: CacheModel.ProductDetailResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(models: List<CacheModel.ProductDetailResponse>)

    @Query("SELECT * FROM $PRODUCT_TABLE WHERE id=:id")
    fun fetch(id: String): CacheModel.ProductDetailResponse?
}

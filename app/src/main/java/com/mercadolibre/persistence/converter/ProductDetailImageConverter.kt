package com.mercadolibre.persistence.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mercadolibre.persistence.model.CacheModel
import java.lang.reflect.Type

class ProductDetailImageConverter {

    @TypeConverter
    fun stringToList(data: String): List<CacheModel.ProductDetailImage> {
        return data.let {
            val listType: Type = object : TypeToken<List<CacheModel.ProductDetailImage?>?>() {}.type
            Gson().fromJson(data, listType) as List<CacheModel.ProductDetailImage>
        }
    }

    @TypeConverter
    fun listToString(someObjects: List<CacheModel.ProductDetailImage>): String {
        return Gson().toJson(someObjects)
    }
}

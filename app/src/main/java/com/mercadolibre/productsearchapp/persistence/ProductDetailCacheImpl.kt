package com.mercadolibre.productsearchapp.persistence

import com.mercadolibre.productsearchapp.persistence.model.CacheModel
import com.mercadolibre.persistence.productdetail.cache.ProductDetailCache
import com.mercadolibre.productsearch.entities.Product

class ProductDetailCacheImpl(
    private val database: AppDatabase
) : ProductDetailCache {

    override fun saveProductDetails(product: Product) {
        database.productDao()
            .insert(
                CacheModel.ProductDetailResponse(
                    id = product.id,
                    title = product.title,
                    thumbnail = product.thumbnail,
                    warranty = product.warranty,
                    availableQuantity = product.availableQuantity,
                )
            )
    }

    override fun getProductDetails(productId: String?): Product {
        return database.productDao()
            .fetch(productId ?: "")
            ?.let {
                Product(
                    id = it.id,
                    title = it.title,
                    thumbnail = it.thumbnail,
                    warranty = it.warranty,
                    availableQuantity = it.availableQuantity,
                )
            } ?: Product()
    }
}

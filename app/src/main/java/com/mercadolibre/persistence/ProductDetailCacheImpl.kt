package com.mercadolibre.persistence

import com.mercadolibre.persistence.productdetail.cache.ProductDetailCache
import com.mercadolibre.productsearch.entities.Product

class ProductDetailCacheImpl(
    private val database: AppDatabase
) : ProductDetailCache {

    override fun getProductDetails(productId: String?): Product {
        return database.productDao()
            .fetch(productId ?: "")
            .let {
                Product(
                    id = it.id,
                    title = it.title,
                    thumbnail = it.thumbnail
                )
            }
    }
}

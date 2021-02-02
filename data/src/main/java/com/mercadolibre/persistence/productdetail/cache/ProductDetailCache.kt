package com.mercadolibre.persistence.productdetail.cache

import com.mercadolibre.productsearch.entities.Product

interface ProductDetailCache {
    fun saveProductDetails(product: Product)
    fun getProductDetails(productId: String?): Product
}

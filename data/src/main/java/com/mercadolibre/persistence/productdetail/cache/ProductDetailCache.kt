package com.mercadolibre.persistence.productdetail.cache

import com.mercadolibre.productsearch.entities.Product

interface ProductDetailCache {
    fun getProductDetails(productId: String?): Product
}

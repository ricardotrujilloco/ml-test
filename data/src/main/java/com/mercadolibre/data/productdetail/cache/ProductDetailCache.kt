package com.mercadolibre.data.productdetail.cache

import com.mercadolibre.productsearch.entities.Product

interface ProductDetailCache {
    fun getProductDetails(): Product
}

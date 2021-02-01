package com.mercadolibre.productsearch.usecases.productdetail

import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository

class ProductDetailInteractorImpl(
    private val repository: Repository<String, Product>
) : ProductDetailInteractor {

    override fun getProductDetails(productId: String): ResponseWrapper<Product> {
        return repository.execute(productId)
    }
}

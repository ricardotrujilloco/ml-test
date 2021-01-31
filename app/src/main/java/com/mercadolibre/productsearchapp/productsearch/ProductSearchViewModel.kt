package com.mercadolibre.productsearchapp.productsearch

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

abstract class ProductSearchViewModel : ViewModel() {
    abstract fun searchProducts(query: String)
    abstract fun getMediator(): MediatorLiveData<String>
}

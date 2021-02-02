package com.mercadolibre.productsearchapp.productsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.productsearchapp.common.model.UiModel

abstract class ProductSearchViewModel : ViewModel() {
    abstract fun searchProducts(query: String)
    abstract fun getMediator(): LiveData<UiModel.SearchResult>
}

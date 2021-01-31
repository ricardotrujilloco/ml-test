package com.mercadolibre.productsearchapp.productsearch

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductSearchViewModelImpl(private val interactor: SearchProductInteractor) :
    ProductSearchViewModel() {
    private val mediator = MediatorLiveData<String>()

    init {
        // searchProducts("canon")
    }

    override fun searchProducts(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val value = withContext(Dispatchers.IO) {
                interactor.searchProductByQuery(query)
            }
            mediator.value = value.response?.size.toString()
        }
    }

    override fun getMediator() = mediator
}

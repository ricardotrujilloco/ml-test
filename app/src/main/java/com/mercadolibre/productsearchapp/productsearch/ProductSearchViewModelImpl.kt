package com.mercadolibre.productsearchapp.productsearch

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductSearchViewModelImpl(private val interactor: SearchProductInteractor) :
    ProductSearchViewModel() {
    private val mediator = MediatorLiveData<List<UiModel.ProductItem>>()

    override fun searchProducts(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val value = withContext(Dispatchers.IO) {
                query.takeIf { it.isNotBlank() }
                    ?.let {
                        interactor.searchProductByQuery(query)
                    }
            }
            mediator.value = value?.response
                ?.takeIf { it.isNotEmpty() }
                ?.map { it -> UiModel.ProductItem(title = it.title) } ?: listOf()
        }
    }

    override fun getMediator() = mediator
}

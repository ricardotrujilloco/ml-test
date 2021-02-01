package com.mercadolibre.productsearchapp.productsearch

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductSearchViewModelImpl(
    private val interactor: SearchProductInteractor
) : ProductSearchViewModel() {
    private val mediator = MediatorLiveData<List<UiModel.Product>>()

    override fun searchProducts(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            mediator.value = withContext(Dispatchers.IO) {
                query.takeIf { it.isNotBlank() }
                    ?.let { interactor.searchProductByQuery(query) }
            }?.response
                ?.takeIf { it.isNotEmpty() }
                ?.map { it ->
                    UiModel.Product(
                        id = it.id,
                        title = it.title
                    )
                } ?: listOf()
        }
    }

    override fun getMediator() = mediator
}

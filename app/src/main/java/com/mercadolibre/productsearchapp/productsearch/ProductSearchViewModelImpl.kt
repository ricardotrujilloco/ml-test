package com.mercadolibre.productsearchapp.productsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel
import com.mercadolibre.productsearchapp.productsearch.mapper.SearchProductUiMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductSearchViewModelImpl(
    private val interactor: SearchProductInteractor,
    private val mapper: SearchProductUiMapper,
    private val backgroundDispatcher: CoroutineDispatcher
) : ProductSearchViewModel() {
    private val mediator = MediatorLiveData<UiModel.SearchResult>()

    override fun searchProducts(query: String) {
        mediator.value = UiModel.SearchResult(SearchState.LOADING)
        viewModelScope.launch(Dispatchers.Main) {
            mediator.value = withContext(backgroundDispatcher) {
                query.takeIf { it.isNotBlank() }?.let { interactor.searchProductByQuery(query) }
            }?.let { responseWrapper ->
                manageResponse(responseWrapper)
            } ?: UiModel.SearchResult(SearchState.SEARCH_HINT)
        }
    }

    private fun manageResponse(responseWrapper: ResponseWrapper<List<Product>>) =
        if (responseWrapper.hasError()) {
            UiModel.SearchResult(SearchState.ERROR)
        } else {
            responseWrapper.response
                ?.takeIf { it.isNotEmpty() }
                ?.map { product -> mapper.mapProductToUiModel(product) }
                ?.let {
                    UiModel.SearchResult(SearchState.SUCCESS, it)
                } ?: UiModel.SearchResult(SearchState.NO_RESULTS)
        }

    override fun getMediator(): LiveData<UiModel.SearchResult> = mediator
}

package com.mercadolibre.productsearchapp.productsearch

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import com.mercadolibre.productsearchapp.common.model.UiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.*

class ProductSearchViewModelImpl(
    private val interactor: SearchProductInteractor
) : ProductSearchViewModel() {
    private val mediator = MediatorLiveData<UiModel.SearchResult>()

    override fun searchProducts(query: String) {
        mediator.value = UiModel.SearchResult(SearchState.LOADING, listOf())
        viewModelScope.launch(Dispatchers.Main) {
            mediator.value = withContext(Dispatchers.IO) {
                query.takeIf { it.isNotBlank() }
                    ?.let { interactor.searchProductByQuery(query) }
            }?.let { responseWrapper ->
                if (responseWrapper.error?.error?.isNotEmpty() == true || responseWrapper.error?.message?.isNotEmpty() == true) {
                    UiModel.SearchResult(SearchState.ERROR, listOf())
                } else {
                    UiModel.SearchResult(SearchState.SUCCESS, responseWrapper.response
                        ?.takeIf { it.isNotEmpty() }
                        ?.map { it ->
                            UiModel.Product(
                                id = it.id,
                                title = it.title,
                                price = some(it.price.toDouble()),
                                thumbnail = it.thumbnail,
                                freeShipping = it.freeShipping
                            )

                        } ?: listOf())
                }
            } ?: UiModel.SearchResult(SearchState.NO_RESULTS, listOf())
        }
    }

    private fun some(amount: Double): String {
        val locale = Locale("es", "AR")
        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(amount)
    }

    override fun getMediator() = mediator
}

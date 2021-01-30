package com.mercadolibre.data.productsearch

import com.mercadolibre.data.productsearch.api.SearchProductsService
import com.mercadolibre.data.productsearch.mapper.BackendResponseMapper
import com.mercadolibre.data.productsearch.model.SearchProductBackendModel
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.ProductsSearchGateway
import com.mercadolibre.productsearch.usecases.UseCaseError
import okhttp3.ResponseBody
import retrofit2.Response

class ProductsSearchGatewayImpl(
    private val service: SearchProductsService,
    private val errorSerializer: BackEndErrorSerializer,
    private val productsMapper: BackendResponseMapper<SearchProductBackendModel.SearchProductResponse, List<Product>>
) : ProductsSearchGateway<List<Product>> {

    override fun searchProductByQuery(query: String): ResponseWrapper<List<Product>> {
        return try {
            service.searchProductsByQuery(query)
                .execute()
                .let(handleResponse)
        } catch (e: Exception) {
            ResponseWrapper(
                response = listOf(),
                error = UseCaseError(e.message)
            )
        }
    }

    private val handleResponse: (Response<SearchProductBackendModel.SearchProductResponse?>) -> ResponseWrapper<List<Product>> =
        { response ->
            response.body()?.let {
                ResponseWrapper(productsMapper.backendModelToEntity(it), UseCaseError())
            } ?: ResponseWrapper(
                response = listOf(),
                error = response.errorBody()?.let {
                    getError(it)
                } ?: UseCaseError()
            )
        }

    private fun getError(errorBody: ResponseBody): UseCaseError {
        return errorSerializer.fromJson(errorBody.string())
            .let { UseCaseError(it.message, it.error) }
    }
}

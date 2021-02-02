package com.mercadolibre.persistence.productsearch

import com.mercadolibre.persistence.common.serialization.BackEndErrorSerializer
import com.mercadolibre.persistence.common.BackendResponseMapper
import com.mercadolibre.persistence.common.log.ErrorLogger
import com.mercadolibre.persistence.productsearch.api.SearchProductsService
import com.mercadolibre.persistence.productsearch.model.SearchProductBackendModel
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import com.mercadolibre.productsearch.usecases.UseCaseError
import okhttp3.ResponseBody
import retrofit2.Response

class ProductsSearchRepository(
    private val service: SearchProductsService,
    private val errorSerializer: BackEndErrorSerializer,
    private val productsMapper: BackendResponseMapper<SearchProductBackendModel.SearchProductResponse, List<Product>>,
    private val logger: ErrorLogger
) : Repository<String, List<Product>> {

    override fun execute(arguments: String?): ResponseWrapper<List<Product>> {
        return try {
            service.searchProductsByQuery(arguments).execute().let(handleResponse)
        } catch (e: Exception) {
            logger.log(javaClass.canonicalName, e)
            ResponseWrapper(error = UseCaseError(e.message))
        }
    }

    private val handleResponse: (Response<SearchProductBackendModel.SearchProductResponse?>) -> ResponseWrapper<List<Product>> =
        { response ->
            response.body()?.let {
                ResponseWrapper(productsMapper.backendModelToEntity(it))
            } ?: ResponseWrapper(error = response.errorBody()?.let { getError(it) }
                ?: UseCaseError())
        }

    private fun getError(errorBody: ResponseBody): UseCaseError {
        return errorSerializer.fromJson(errorBody.string())
            .let { UseCaseError(it.message, it.error) }
    }
}

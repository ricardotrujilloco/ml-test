package com.mercadolibre.persistence.productdetail

import com.mercadolibre.persistence.common.serialization.BackEndErrorSerializer
import com.mercadolibre.persistence.common.BackendResponseMapper
import com.mercadolibre.persistence.productdetail.api.ProductDetailService
import com.mercadolibre.persistence.productdetail.cache.ProductDetailCache
import com.mercadolibre.persistence.productdetail.model.ProductDetailsBackendModel
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.ResponseWrapper
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import com.mercadolibre.productsearch.usecases.UseCaseError
import okhttp3.ResponseBody
import retrofit2.Response

class ProductDetailRepository(
    private val service: ProductDetailService,
    private val cache: ProductDetailCache,
    private val errorSerializer: BackEndErrorSerializer,
    private val productsMapper: BackendResponseMapper<ProductDetailsBackendModel.ProductDetailResponse, Product>
) : Repository<String, Product> {

    override fun execute(arguments: String?): ResponseWrapper<Product> {
        return try {
            service.getProductDetails(arguments).execute().let(handleResponse)
        } catch (e: Exception) {
            cache.getProductDetails().takeIf { it.id.isNotBlank() }?.let { ResponseWrapper(it) }
                ?: ResponseWrapper(error = UseCaseError(e.message))
        }
    }

    private val handleResponse: (Response<ProductDetailsBackendModel.ProductDetailResponse?>) -> ResponseWrapper<Product> =
        { response ->
            response.body()?.let {
                ResponseWrapper(productsMapper.backendModelToEntity(it))
            } ?: cache.getProductDetails().takeIf { it.id.isNotBlank() }?.let { ResponseWrapper(it) }
            ?: ResponseWrapper(error = response.errorBody()?.let { getError(it) }
                ?: UseCaseError())
        }

    private fun getError(errorBody: ResponseBody): UseCaseError {
        return errorSerializer.fromJson(errorBody.string())
            .let { UseCaseError(it.message, it.error) }
    }
}

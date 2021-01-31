package com.mercadolibre.di

import com.google.gson.Gson
import com.mercadolibre.persistence.common.BackendResponseMapper
import com.mercadolibre.persistence.common.serialization.BackEndErrorSerializer
import com.mercadolibre.persistence.common.serialization.BackEndErrorSerializerImpl
import com.mercadolibre.persistence.productsearch.ProductsSearchRepository
import com.mercadolibre.persistence.productsearch.api.SearchProductsService
import com.mercadolibre.persistence.productsearch.mapper.ProductsSearchBackendResponseMapperImpl
import com.mercadolibre.persistence.productsearch.model.SearchProductBackendModel
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractorImpl
import com.mercadolibre.productsearchapp.BuildConfig
import com.mercadolibre.productsearchapp.productsearch.ProductSearchViewModel
import com.mercadolibre.productsearchapp.productsearch.ProductSearchViewModelImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

val productSearchModule = module {
    factory<SearchProductsService> { get<Retrofit>().create(SearchProductsService::class.java) }
    factory<BackEndErrorSerializer> { BackEndErrorSerializerImpl(Gson()) }
    factory<BackendResponseMapper<SearchProductBackendModel.SearchProductResponse, List<Product>>> { ProductsSearchBackendResponseMapperImpl() }
    factory<Repository<String, List<Product>>> { ProductsSearchRepository(get(), get(), get()) }
    factory<SearchProductInteractor> { SearchProductInteractorImpl(get()) }
    viewModel<ProductSearchViewModel> { ProductSearchViewModelImpl(get()) }
}

object ModulesList {
    val appModules = listOf(
        dataModule,
        productSearchModule
    )
}

package com.mercadolibre.di

import android.app.Application
import android.util.SparseArray
import androidx.room.Room
import com.google.gson.Gson
import com.mercadolibre.persistence.common.BackendResponseMapper
import com.mercadolibre.persistence.common.log.ErrorLogger
import com.mercadolibre.persistence.common.serialization.BackEndErrorSerializer
import com.mercadolibre.persistence.common.serialization.BackEndErrorSerializerImpl
import com.mercadolibre.persistence.productdetail.ProductDetailRepository
import com.mercadolibre.persistence.productdetail.api.ProductDetailService
import com.mercadolibre.persistence.productdetail.cache.ProductDetailCache
import com.mercadolibre.persistence.productdetail.mapper.ProductDetailBackendResponseMapperImpl
import com.mercadolibre.persistence.productdetail.model.ProductDetailsBackendModel
import com.mercadolibre.persistence.productsearch.ProductsSearchRepository
import com.mercadolibre.persistence.productsearch.api.SearchProductsService
import com.mercadolibre.persistence.productsearch.mapper.ProductsSearchBackendResponseMapperImpl
import com.mercadolibre.persistence.productsearch.model.SearchProductBackendModel
import com.mercadolibre.productsearch.entities.Product
import com.mercadolibre.productsearch.interfaceadapters.gateways.api.Repository
import com.mercadolibre.productsearch.usecases.productdetail.ProductDetailInteractor
import com.mercadolibre.productsearch.usecases.productdetail.ProductDetailInteractorImpl
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractor
import com.mercadolibre.productsearch.usecases.productsearch.SearchProductInteractorImpl
import com.mercadolibre.productsearchapp.BuildConfig
import com.mercadolibre.productsearchapp.R
import com.mercadolibre.productsearchapp.common.list.AdapterConstants
import com.mercadolibre.productsearchapp.common.list.AppListAdapter
import com.mercadolibre.productsearchapp.common.list.AppListAdapterImpl
import com.mercadolibre.productsearchapp.common.list.ViewTypeDelegateAdapter
import com.mercadolibre.productsearchapp.common.log.AppErrorLogger
import com.mercadolibre.productsearchapp.common.navigation.AppNavigator
import com.mercadolibre.productsearchapp.cache.AppDatabase
import com.mercadolibre.productsearchapp.cache.ProductDetailCacheImpl
import com.mercadolibre.productsearchapp.productdetail.ProductDetailUiMapper
import com.mercadolibre.productsearchapp.productdetail.ProductDetailUiMapperImpl
import com.mercadolibre.productsearchapp.productdetail.ProductDetailViewModel
import com.mercadolibre.productsearchapp.productdetail.ProductDetailViewModelImpl
import com.mercadolibre.productsearchapp.productsearch.ProductSearchViewModel
import com.mercadolibre.productsearchapp.productsearch.ProductSearchViewModelImpl
import com.mercadolibre.productsearchapp.productsearch.adapter.ProductDelegateAdapter
import com.mercadolibre.productsearchapp.productsearch.mapper.SearchProductUiMapper
import com.mercadolibre.productsearchapp.productsearch.mapper.SearchProductUiMapperImpl
import com.mercadolibre.productsearchapp.productsearch.navigation.ProductSearchNavigator
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

val PRODUCTS_SEARCH = StringQualifier("PRODUCTS_SEARCH")
val PRODUCT_DETAIL = StringQualifier("PRODUCT_DETAIL")

val appModule = module {
    factory { Locale("es", "AR") }
    factory<ErrorLogger> { AppErrorLogger() }
    factory { Dispatchers.IO }
}

val productSearchModule = module {
    factory {
        SparseArray<ViewTypeDelegateAdapter>().apply {
            put(AdapterConstants.PRODUCT, ProductDelegateAdapter())
        }
    }
    factory<AppListAdapter> { AppListAdapterImpl(get()) }
    factory<AppNavigator<String>> { ProductSearchNavigator() }
    factory<SearchProductsService> { get<Retrofit>().create(SearchProductsService::class.java) }
    factory<BackendResponseMapper<SearchProductBackendModel.SearchProductResponse, List<Product>>>(
        PRODUCTS_SEARCH
    ) { ProductsSearchBackendResponseMapperImpl() }
    factory<Repository<String, List<Product>>>(PRODUCTS_SEARCH) {
        ProductsSearchRepository(get(), get(), get(PRODUCTS_SEARCH), get())
    }
    factory<SearchProductInteractor> { SearchProductInteractorImpl(get(PRODUCTS_SEARCH)) }
    factory<SearchProductUiMapper> { SearchProductUiMapperImpl(get()) }
    viewModel<ProductSearchViewModel> { ProductSearchViewModelImpl(get(), get(), get()) }
}

val productDetailModule = module {
    factory<ProductDetailService> { get<Retrofit>().create(ProductDetailService::class.java) }
    factory<BackendResponseMapper<ProductDetailsBackendModel.ProductDetailResponse, Product>>(
        PRODUCT_DETAIL
    ) { ProductDetailBackendResponseMapperImpl() }
    factory<ProductDetailCache> { ProductDetailCacheImpl(get()) }
    factory<Repository<String, Product>>(PRODUCT_DETAIL) {
        ProductDetailRepository(get(), get(), get(), get(PRODUCT_DETAIL), get())
    }
    factory<ProductDetailInteractor> { ProductDetailInteractorImpl(get(PRODUCT_DETAIL)) }
    factory<ProductDetailUiMapper> { ProductDetailUiMapperImpl(get()) }
    viewModel<ProductDetailViewModel> {
        ProductDetailViewModelImpl(get(), get(), get())
    }
}

val dataModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    factory<BackEndErrorSerializer> { BackEndErrorSerializerImpl(Gson()) }
    single {
        Room.databaseBuilder(
            get<Application>(),
            AppDatabase::class.java,
            get<Application>().getString(R.string.db_id)
        ).build()
    }
}

object ModulesList {
    val appModules = listOf(
        appModule,
        productSearchModule,
        productDetailModule,
        dataModule
    )
}

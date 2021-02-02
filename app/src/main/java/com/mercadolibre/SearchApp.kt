package com.mercadolibre

import android.app.Application
import com.mercadolibre.di.ModulesList.appModules
import com.mercadolibre.productsearchapp.common.log.AppErrorLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.lang.Exception

class SearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SearchApp)
            modules(appModules)
        }
    }
}

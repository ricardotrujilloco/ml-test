package com.mercadolibre

import android.app.Application
import com.mercadolibre.di.ModulesList.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SearchApp)
            modules(appModules)
        }
    }
}

package com.mercadolibre.productsearchapp.common.navigation

import android.app.Activity
import androidx.fragment.app.FragmentActivity

interface AppNavigator<ARGUMENT> {
    fun next(activity: Activity?, argument: ARGUMENT?)
}

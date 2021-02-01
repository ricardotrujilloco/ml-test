package com.mercadolibre.productsearchapp.productsearch.navigation

import android.app.Activity
import androidx.navigation.Navigation
import com.mercadolibre.productsearchapp.R
import com.mercadolibre.productsearchapp.common.navigation.AppNavigator
import com.mercadolibre.productsearchapp.productsearch.ProductSearchFragmentDirections

class ProductSearchNavigator : AppNavigator<String> {

    override fun next(activity: Activity?, argument: String?) {
        activity?.let {
            Navigation.findNavController(it, R.id.mainViewFragmentController)
                .navigate(
                    ProductSearchFragmentDirections
                        .actionProductSearchFragmentToProductDetailActivity(argument ?: "")
                )
        }
    }

    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
    }
}

package com.mercadolibre.productsearchapp.productsearch

import androidx.fragment.app.Fragment
import com.mercadolibre.factory.FragmentCreator

class ProductSearchFragmentCreator(
    private val viewModel: ProductSearchViewModel
) : FragmentCreator {

    override fun get(): Fragment {
        return ProductSearchFragment(viewModel)
    }
}

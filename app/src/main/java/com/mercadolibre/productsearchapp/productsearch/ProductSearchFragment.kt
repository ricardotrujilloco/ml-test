package com.mercadolibre.productsearchapp.productsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mercadolibre.productsearchapp.databinding.FragmentProductSearchBinding

class ProductSearchFragment(viewModel: ProductSearchViewModel) : Fragment() {
    private lateinit var viewBinding: FragmentProductSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentProductSearchBinding.inflate(
            LayoutInflater.from(context), null, true
        );
        return viewBinding.root
    }
}

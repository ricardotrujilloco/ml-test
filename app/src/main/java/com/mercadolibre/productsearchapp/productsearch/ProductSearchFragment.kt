package com.mercadolibre.productsearchapp.productsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mercadolibre.productsearchapp.databinding.FragmentProductSearchBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProductSearchFragment : Fragment() {
    private val viewModel: ProductSearchViewModel by viewModel()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getMediator().observe(viewLifecycleOwner, {
            viewBinding.testField.text = it
        })
    }
}

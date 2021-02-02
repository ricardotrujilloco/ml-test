package com.mercadolibre.productsearchapp.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mercadolibre.productsearchapp.databinding.FragmentProductDetailBinding
import com.mercadolibre.productsearchapp.productsearch.navigation.ProductSearchNavigator.Companion.PRODUCT_ID
import org.koin.android.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {
    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var viewBinding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentProductDetailBinding.inflate(
            LayoutInflater.from(context), null, true
        )
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewBinding
                .setUpToolbar(activity)
                .setErrorCallToActionListener { getProductDetails() }
        }
        viewModel.getMediator().observe(viewLifecycleOwner, {
            it.takeIf { it.state == ProductDetailState.ERROR }?.let { viewBinding.showError() }
            viewBinding.setProductDetails(it)
        })
        getProductDetails()
    }

    private fun getProductDetails() {
        viewBinding.hideError()
        viewModel.getProductDetails(arguments?.getString(PRODUCT_ID) ?: "")
    }
}

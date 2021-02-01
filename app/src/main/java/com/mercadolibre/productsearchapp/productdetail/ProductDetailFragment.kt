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
        getProductDetails(arguments?.getString(PRODUCT_ID))
        return viewBinding.root
    }

    private fun getProductDetails(productId: String?) {
        productId?.takeIf { it.isNotBlank() }
            ?.let {
                viewBinding.productTitle.text = it
                viewModel.getProductDetails(it)
            }
    }
}

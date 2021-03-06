package com.mercadolibre.productsearchapp.productdetail

import android.app.Activity
import android.view.View.GONE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.mercadolibre.productsearchapp.R
import com.mercadolibre.productsearchapp.common.model.UiModel
import com.mercadolibre.productsearchapp.databinding.FragmentProductDetailBinding

fun FragmentProductDetailBinding.setUpToolbar(activity: Activity): FragmentProductDetailBinding =
    apply {
        productDetailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        productDetailToolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

fun FragmentProductDetailBinding.showError(): FragmentProductDetailBinding =
    apply {
        errorGroup.visibility = VISIBLE
    }

fun FragmentProductDetailBinding.hideError(): FragmentProductDetailBinding =
    apply {
        errorGroup.visibility = GONE
    }

fun FragmentProductDetailBinding.setProductDetails(productDetail: UiModel.ProductDetailResult): FragmentProductDetailBinding =
    apply {
        productDetail.product?.let { productDetail ->
            productTitle.text = productDetail.title
            productPrice.text = productDetail.price
            productDetail.image.takeIf { it.isNotBlank() }
                ?.let {
                    Glide.with(this.root).load(it).into(productImage)
                }
            productAvailability.text = productDetail.availableQuantity
            productWarranty.text = productDetail.warranty
        }
    }

fun FragmentProductDetailBinding.setErrorCallToActionListener(action: () -> Unit): FragmentProductDetailBinding =
    apply {
        errorMessageAction.setOnClickListener { action() }
    }

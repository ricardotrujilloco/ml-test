package com.mercadolibre.productsearchapp.productdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.mercadolibre.productsearchapp.R

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        findNavController(R.id.productDetailViewFragmentController)
            .setGraph(R.navigation.nav_product_detail, intent.extras)
    }
}

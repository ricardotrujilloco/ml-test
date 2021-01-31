package com.mercadolibre

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import com.mercadolibre.productsearchapp.R
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val fragmentFactory: FragmentFactory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

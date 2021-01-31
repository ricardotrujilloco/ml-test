package com.mercadolibre.productsearchapp.productsearch

import android.content.Context
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercadolibre.productsearchapp.common.list.AppListAdapter
import com.mercadolibre.productsearchapp.databinding.FragmentProductSearchBinding

fun FragmentProductSearchBinding.setUpListManager(context: Context): FragmentProductSearchBinding =
    apply {
        val linearLayout = LinearLayoutManager(context)
        productsList.layoutManager = linearLayout
    }

fun FragmentProductSearchBinding.setAdapter(listAdapter: AppListAdapter): FragmentProductSearchBinding =
    apply {
        productsList.adapter = listAdapter
    }

fun FragmentProductSearchBinding.setSearchFieldChangeListener(action: (value: String) -> Unit): FragmentProductSearchBinding =
    apply {
        searchField.addTextChangedListener { action(it.toString()) }
    }

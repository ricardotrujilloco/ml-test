package com.mercadolibre.productsearchapp.common.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.productsearchapp.productsearch.adapter.OnViewSelectedListener

interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
    fun setListener(listener: OnViewSelectedListener)
}

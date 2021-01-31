package com.mercadolibre.productsearchapp.common.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


interface AdapterDelegate<T> {
    fun isForViewType(items: T, position: Int): Boolean
    fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder
    fun onBindViewHolder(items: T, position: Int, holder: RecyclerView.ViewHolder)
}

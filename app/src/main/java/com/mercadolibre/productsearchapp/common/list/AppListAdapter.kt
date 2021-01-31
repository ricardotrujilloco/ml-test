package com.mercadolibre.productsearchapp.common.list

import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.productsearchapp.productsearch.adapter.OnViewSelectedListener

abstract class AppListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    abstract fun setListener(listener: OnViewSelectedListener)
    abstract fun setItems(newItems: List<ViewType>)
}

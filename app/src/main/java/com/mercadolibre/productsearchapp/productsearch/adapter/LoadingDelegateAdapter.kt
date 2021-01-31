package com.mercadolibre.productsearchapp.productsearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.productsearchapp.R
import com.mercadolibre.productsearchapp.common.list.ViewType
import com.mercadolibre.productsearchapp.common.list.ViewTypeDelegateAdapter

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
    private lateinit var listener: OnViewSelectedListener

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_loading, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    override fun setListener(listener: OnViewSelectedListener) {
        this.listener = listener
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

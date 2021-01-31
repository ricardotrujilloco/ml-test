package com.mercadolibre.productsearchapp.common.list

import android.util.SparseArray
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.productsearchapp.productsearch.adapter.OnViewSelectedListener
import java.util.*

class AppListAdapterImpl(private val delegateAdapters: SparseArray<ViewTypeDelegateAdapter>) :
    AppListAdapter() {
    private val items: ArrayList<ViewType> = ArrayList()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        items.add(loadingItem)
    }

    override fun setListener(listener: OnViewSelectedListener) {
        delegateAdapters.forEach { _, adapter -> adapter.setListener(listener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegateAdapters.get(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    override fun getItemCount(): Int = items.size

    override fun setItems(newItems: List<ViewType>) {
        val initPosition = getLastPosition()
        items.takeIf { it.size == 1 }
            ?.let {
                items.removeAt(initPosition)
                notifyItemRemoved(initPosition)
            }
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}

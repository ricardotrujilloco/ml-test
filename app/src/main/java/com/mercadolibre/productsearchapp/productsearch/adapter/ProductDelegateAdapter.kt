package com.mercadolibre.productsearchapp.productsearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mercadolibre.productsearchapp.R
import com.mercadolibre.productsearchapp.common.list.ViewType
import com.mercadolibre.productsearchapp.common.list.ViewTypeDelegateAdapter
import com.mercadolibre.productsearchapp.common.model.UiModel

class ProductDelegateAdapter() : ViewTypeDelegateAdapter {
    private var listener: OnViewSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as ProductViewHolder
        holder.bind(item as UiModel.Product, holder.itemView)
    }

    override fun setListener(listener: OnViewSelectedListener) {
        this.listener = listener
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.productTitle)
        private val price: TextView = view.findViewById(R.id.productPrice)
        private val shippingTag: TextView = view.findViewById(R.id.shippingTag)
        private val thumbnail: ImageView = view.findViewById(R.id.productThumbnail)

        /*private val description = itemView.description
        private val author = itemView.author
        private val comments = itemView.comments
        private val time = itemView.time*/

        fun bind(item: UiModel.Product, itemView: View) {
            title.text = item.title
            price.text = item.price
            item.freeShipping.takeIf { it }?.let { shippingTag.visibility = VISIBLE }
            Glide.with(itemView).load(item.thumbnail).into(thumbnail)
            super.itemView.setOnClickListener { listener?.onItemSelected(item.id) }
            /*imgThumbnail.loadImg(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()

            super.itemView.setOnClickListener { viewSelectedListener.onItemSelected(item.url)}*/
        }
    }
}

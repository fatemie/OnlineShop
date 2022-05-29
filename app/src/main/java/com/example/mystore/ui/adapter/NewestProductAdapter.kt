package com.example.mystore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mystor.R
import com.example.mystore.data.model.ProductsApiResultItem


typealias ItemClickHandler = (ProductsApiResultItem) -> Unit

class NewestProductAdapter(var onWordClicked: ItemClickHandler) :
    ListAdapter<ProductsApiResultItem, NewestProductAdapter.ViewHolder>(WordDiffCallback) {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivProductPicture = view.findViewById<ImageView>(R.id.iv_picture)
        var tvProductTitle = view.findViewById<TextView>(R.id.tv_title)
        var productLauout = view.findViewById<ConstraintLayout>(R.id.constraint)

        fun bind(product: ProductsApiResultItem, onWordClicked: ItemClickHandler) {
            tvProductTitle.text = product.name
            productLauout.setOnClickListener {
                onWordClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): NewestProductAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.newest_product_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewestProductAdapter.ViewHolder, position: Int) {
        val word = getItem(position)
        holder.bind(word, onWordClicked)
    }

}

object WordDiffCallback : DiffUtil.ItemCallback<ProductsApiResultItem>() {
    override fun areItemsTheSame(
        oldItem: ProductsApiResultItem,
        newItem: ProductsApiResultItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ProductsApiResultItem,
        newItem: ProductsApiResultItem
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
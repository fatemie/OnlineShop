package com.example.mystore.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystor.R
import com.example.mystore.data.model.ProductsApiResultItem


class CategoriesAdapter(var onProductClicked: ItemClickHandler) :
    ListAdapter<ProductsApiResultItem, CategoriesAdapter.ViewHolder>(CategoriesDiffCallback) {


    class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        var ivProductPicture = view.findViewById<ImageView>(R.id.iv_picture)
        var tvProductTitle = view.findViewById<TextView>(R.id.tv_title)
        var productLauout = view.findViewById<ConstraintLayout>(R.id.constraint)

        fun bind(product: ProductsApiResultItem, onProductClicked: ItemClickHandler) {
            tvProductTitle.text = product.name
            Glide.with(context)
                .load(product.images[0].src)
                .fitCenter()
                .into(ivProductPicture)
            productLauout.setOnClickListener {
                onProductClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_product_item, viewGroup, false)
        return ViewHolder(view, viewGroup.context)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, onProductClicked)
    }

}

object CategoriesDiffCallback : DiffUtil.ItemCallback<ProductsApiResultItem>() {
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
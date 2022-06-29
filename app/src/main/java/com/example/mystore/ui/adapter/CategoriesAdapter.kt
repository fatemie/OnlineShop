package com.example.mystore.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystor.R
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesItem

typealias CategoryClickHandler = (CategoriesItem) -> Unit

class CategoriesAdapter(var onCategoryClicked: CategoryClickHandler) :
    ListAdapter<CategoriesItem, CategoriesAdapter.ViewHolder>(CategoriesDiffCallback) {


    class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        var ivCategoryPicture = view.findViewById<ImageView>(R.id.ivCategoryPicture)
        var tvCategoryTitle = view.findViewById<TextView>(R.id.tvCategoryName)
        var categoryCard = view.findViewById<CardView>(R.id.categoryCard)

        fun bind(category: CategoriesItem, onCategoryClicked: CategoryClickHandler) {
            tvCategoryTitle.text = category.name
            Glide.with(context)
                .load(category.image.src)
                .fitCenter()
                .into(ivCategoryPicture)
            categoryCard.setOnClickListener {
                onCategoryClicked(category)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.categories_rv_item, viewGroup, false)
        return ViewHolder(view,viewGroup.context)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, onCategoryClicked)
    }

}

object CategoriesDiffCallback : DiffUtil.ItemCallback<CategoriesItem>() {
    override fun areItemsTheSame(
        oldItem: CategoriesItem,
        newItem: CategoriesItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CategoriesItem,
        newItem: CategoriesItem
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
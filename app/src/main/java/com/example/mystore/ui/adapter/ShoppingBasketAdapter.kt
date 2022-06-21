package com.example.mystore.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystor.R
import com.example.mystore.data.model.ProductsApiResultItem

typealias ItemProductChangedHandler = (Int) -> Unit

class ShoppingBasketAdapter(var onProductClicked: ItemClickHandler , var onProductDeleted : ItemProductChangedHandler) :
    ListAdapter<ProductsApiResultItem, ShoppingBasketAdapter.ViewHolder>(BasketProductsDiffCallback) {

    class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        var ivProductPicture = view.findViewById<ImageView>(R.id.iv_picture)
        var tvProductTitle = view.findViewById<TextView>(R.id.tv_title)
        var productLauout = view.findViewById<ConstraintLayout>(R.id.constraint)
        val btnDcr = view.findViewById<ImageButton>(R.id.btnDcr)
        val btnRlz = view.findViewById<ImageButton>(R.id.btnRlz)
        val tvCount = view.findViewById<TextView>(R.id.tvCount)

        fun bind(product: ProductsApiResultItem, onProductClicked: ItemClickHandler, onProductChanged : ItemProductChangedHandler) {
            tvProductTitle.text = product.name
            tvCount.text = product.numberInBasket.toString()
            Glide.with(context)
                .load(product.images[0].src)
                .fitCenter()
                .into(ivProductPicture)
            productLauout.setOnClickListener {
                onProductClicked(product)
            }
            btnDcr.setOnClickListener {
                if (product.numberInBasket != 1) {
                    product.numberInBasket--
                    onProductChanged(product.id)

                    tvCount.text = product.numberInBasket.toString()
                    if (product.numberInBasket == 1)
                        btnDcr.setImageResource(R.drawable.ic_baseline_delete_24)
                } else {
                    product.numberInBasket = 0
                    onProductChanged(product.id)
                }
            }
            btnRlz.setOnClickListener {
                btnDcr.setImageResource(R.drawable.ic_baseline_exposure_neg_1_24)
                product.numberInBasket++
                onProductChanged(product.id)
                tvCount.text = product.numberInBasket.toString()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.shopping_basket_recycler, viewGroup, false)
        return ViewHolder(view, viewGroup.context)
    }

    override fun onBindViewHolder(holder: ShoppingBasketAdapter.ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, onProductClicked, onProductDeleted)
    }


    fun deleteItem(position: Int, list: MutableList<ProductsApiResultItem>?) {
        if (list != null) {
           list.removeAt(position)
            submitList(list)
        }
    }

    object BasketProductsDiffCallback : DiffUtil.ItemCallback<ProductsApiResultItem>() {
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
    }
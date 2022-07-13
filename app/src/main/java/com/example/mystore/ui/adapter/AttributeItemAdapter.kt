package com.example.mystore.ui.adapter

import android.content.Context
import android.util.Log
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
import com.example.mystore.data.model.attributeTerm.AttributeTermItem

typealias AtttributeItemClickHandler = (AttributeTermItem) -> Unit

class AttributeItemAdapter(var onItemClicked: AtttributeItemClickHandler) :
    ListAdapter<AttributeTermItem, AttributeItemAdapter.ViewHolder>(AttributeDiffCallback) {


    class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        var tvAttributeName = view.findViewById<TextView>(R.id.tvAttributeName)
        var ivChoose = view.findViewById<ImageView>(R.id.ivCircle)


        fun bind(attributeTerm: AttributeTermItem, onItemClicked: AtttributeItemClickHandler, list : List<AttributeTermItem>) {
            tvAttributeName.text = attributeTerm.name

            var isChoose = attributeTerm.isChoosed
            if(isChoose){
                ivChoose.setImageResource(R.drawable.ic_baseline_brightness_1_24)
            }else{
                ivChoose.setImageResource(R.drawable.ic_baseline_album_24)
            }

            ivChoose.setOnClickListener {
//                for (item in list){
//                    if (item.id != attributeTerm.id){
//                        item.isChoosed = false
//                    }
//                }
                if (isChoose){
                    ivChoose.setImageResource(R.drawable.ic_baseline_brightness_1_24)
                    attributeTerm.isChoosed = true
                }else{
                    ivChoose.setImageResource(R.drawable.ic_baseline_album_24)
                    attributeTerm.isChoosed = false
                }
                isChoose = !isChoose
                Log.e("tag",list[0].isChoosed.toString() )
                onItemClicked(attributeTerm)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.attribute_term_view, viewGroup, false)
        return ViewHolder(view,viewGroup.context)
    }

    override fun onBindViewHolder(holder: AttributeItemAdapter.ViewHolder, position: Int) {
        val attribute_term = getItem(position)
        submitList(currentList )
        holder.bind(attribute_term, onItemClicked, currentList)
    }

}

object AttributeDiffCallback : DiffUtil.ItemCallback<AttributeTermItem>() {
    override fun areItemsTheSame(
        oldItem: AttributeTermItem,
        newItem: AttributeTermItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AttributeTermItem,
        newItem: AttributeTermItem
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
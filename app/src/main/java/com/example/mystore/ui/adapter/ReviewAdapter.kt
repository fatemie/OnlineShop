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
import com.example.mystore.data.model.review.ReviewItem


class ReviewAdapter() :
    ListAdapter<ReviewItem, ReviewAdapter.ViewHolder>(ReviewsDiffCallback) {


    class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        var ivReviewerAvatar = view.findViewById<ImageView>(R.id.ivReviewerAvatar)
        var tvReviewerName = view.findViewById<TextView>(R.id.tvReviewerName)
        var tvReview = view.findViewById<TextView>(R.id.tvReview)

        fun bind(review: ReviewItem) {
            tvReviewerName.text = review.reviewer
            tvReview.text = review.reviewDescription
            Glide.with(context)
                .load(review.reviewerAvatarUrls.x48)
                .fitCenter()
                .into(ivReviewerAvatar)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.review_rv_item, viewGroup, false)
        return ViewHolder(view,viewGroup.context)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

}

object ReviewsDiffCallback : DiffUtil.ItemCallback<ReviewItem>() {
    override fun areItemsTheSame(
        oldItem: ReviewItem,
        newItem: ReviewItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ReviewItem,
        newItem: ReviewItem
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
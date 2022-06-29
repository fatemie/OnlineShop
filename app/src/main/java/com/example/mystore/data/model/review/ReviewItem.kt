package com.example.mystore.data.model.review


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewItem(
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "rating")
    val rating: Int,
    @Json(name = "reviewer")
    val reviewer: String,
    @Json(name = "reviewer_avatar_urls")
    val reviewerAvatarUrls: ReviewerAvatarUrls,
    @Json(name = "reviewer_email")
    val reviewerEmail: String,
    @Json(name = "review")
    var reviewDescription: String,
//    @Json(name = "date_created")
//    val dateCreated: String,
//    @Json(name = "date_created_gmt")
//    val dateCreatedGmt: String,
//    @Json(name = "id")
//    val id: Int,
//    @Json(name = "status")
//    val status: String,
//    @Json(name = "verified")
//    val verified: Boolean,
//    @Json(name = "_links")
//    val links: Links,
)
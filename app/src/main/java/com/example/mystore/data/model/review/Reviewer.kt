package com.example.mystore.data.model.review


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reviewer(
    @Json(name = "embeddable")
    val embeddable: Boolean,
    @Json(name = "href")
    val href: String
)
package com.example.mystore.data.model.customer


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Collection(
    @Json(name = "href")
    val href: String
)
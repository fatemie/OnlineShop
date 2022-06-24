package com.example.mystore.data.model.attributeTerm


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Self(
    @Json(name = "href")
    val href: String
)
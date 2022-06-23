package com.example.mystore.data.model.order


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Refund(
    @Json(name = "id")
    val id: Int,
    @Json(name = "refund")
    val refund: String,
    @Json(name = "total")
    val total: String
)
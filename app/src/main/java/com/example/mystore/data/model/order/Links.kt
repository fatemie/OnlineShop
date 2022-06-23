package com.example.mystore.data.model.order


import com.example.mystore.data.model.Collection
import com.example.mystore.data.model.Self
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    @Json(name = "collection")
    val collection: List<Collection>,
    @Json(name = "customer")
    val customer: List<Customer>,
    @Json(name = "self")
    val self: List<Self>
)
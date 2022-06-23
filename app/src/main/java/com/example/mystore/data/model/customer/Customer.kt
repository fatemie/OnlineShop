package com.example.mystore.data.model.customer

import com.example.mystore.data.model.order.Shipping
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Customer (
    @Json(name = "email")
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "billing")
    val billing: Billing,
    @Json(name = "shipping")
    val shipping: Shipping,

    )
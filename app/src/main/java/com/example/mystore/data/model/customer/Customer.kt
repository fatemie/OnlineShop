package com.example.mystore.data.model.customer

import com.squareup.moshi.Json

data class Customer (
    @Json(name = "id")
    val id: Int,
    @Json(name = "email")
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "billing")
    val billing: Billing,
//    @Json(name = "shipping")
//    val shipping: Shipping,
    )
package com.example.mystore.data.model.order


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShippingLine(
    @Json(name = "id")
    val id: Int,
    @Json(name = "meta_data")
    val metaData: List<MetaDataX>,
    @Json(name = "method_id")
    val methodId: String,
    @Json(name = "method_title")
    val methodTitle: String,
    @Json(name = "taxes")
    val taxes: List<Any>,
    @Json(name = "total")
    val total: String,
    @Json(name = "total_tax")
    val totalTax: String
)
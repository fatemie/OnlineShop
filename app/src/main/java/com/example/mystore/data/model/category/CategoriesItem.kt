package com.example.mystore.data.model.category


import com.example.mystore.data.model.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesItem(
    @Json(name = "count")
    val count: Int,
    @Json(name = "description")
    val description: String,
    @Json(name = "display")
    val display: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: Image,
    @Json(name = "menu_order")
    val menuOrder: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "parent")
    val parent: Int,
    @Json(name = "slug")
    val slug: String
    //@Json(name = "_links")
    //val links: Links,
)
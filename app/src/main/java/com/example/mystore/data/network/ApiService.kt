package com.example.mystore.data.network

import com.example.mystore.data.model.ProductsApiResultItem
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
private const val CONSUMER_KEY = "ck_6b55bb0ff3ea0b7bf4c0aa879af50061964ce38f"
private const val CONSUMER_SECRET = "cs_9b09e5125acffdd27bbe72843ced49db5f8bffb4"

interface ApiService {
    @GET("products")
    suspend fun getNewestProducts(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") page: String = CONSUMER_SECRET
    ): List<ProductsApiResultItem>
}
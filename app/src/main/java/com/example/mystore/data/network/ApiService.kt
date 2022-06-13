package com.example.mystore.data.network

import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesApiResultItem
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
private const val CONSUMER_KEY = "ck_6b55bb0ff3ea0b7bf4c0aa879af50061964ce38f"
private const val CONSUMER_SECRET = "cs_9b09e5125acffdd27bbe72843ced49db5f8bffb4"

interface ApiService {

    @GET("products")
    suspend fun getProducts(
        @Query("per_page") page: Int = 55,
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET

    ): List<ProductsApiResultItem>

    @GET("products")
    suspend fun getProductsOrderBy(
        @Query("per_page") page: Int = 55,
        @Query("orderby") orderBy: String,
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET

    ): List<ProductsApiResultItem>


    @GET("products/categories")
    suspend fun getCategories(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET

    ): List<CategoriesApiResultItem>


    @GET("products")
    suspend fun getProductsInCategory(
        @Query("per_page") page: Int = 30,
        @Query("category") category: String ,
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET

    ): List<ProductsApiResultItem>



}
package com.example.mystore.data.network

import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesApiResultItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

const val BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
private const val CONSUMER_KEY = "ck_6b55bb0ff3ea0b7bf4c0aa879af50061964ce38f"
private const val CONSUMER_SECRET = "cs_9b09e5125acffdd27bbe72843ced49db5f8bffb4"

interface ApiService {

    @GET("products")
    suspend fun getProductsOrderBy(
        @Query("per_page") page: Int = 55,
        @Query("orderby") orderBy: String,
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions()
    ): List<ProductsApiResultItem>


    @GET("products/categories")
    suspend fun getCategories(
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions()
    ): List<CategoriesApiResultItem>


    @GET("products")
    suspend fun getProductsInCategory(
        @Query("per_page") page: Int = 30,
        @Query("category") category: String ,
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions()
    ): List<ProductsApiResultItem>

    @GET("products")
    suspend fun getSearchedProducts(
        @Query("per_page") page: Int = 55,
        @Query("search") search: String ,
        @Query("orderby") orderBy: String ,
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions()
    ): List<ProductsApiResultItem>


    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int,
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions()
    ): ProductsApiResultItem

    @GET("products")
    suspend fun getRelatedProducts(
        @Query("include") str : String,
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions()
    ): List<ProductsApiResultItem>


}
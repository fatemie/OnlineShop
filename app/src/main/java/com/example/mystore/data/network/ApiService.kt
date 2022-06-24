package com.example.mystore.data.network

import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.attributeTerm.AttributeTermItem
import com.example.mystore.data.model.category.CategoriesItem
import com.example.mystore.data.model.customer.Customer
import com.example.mystore.data.model.order.OrderItem
import retrofit2.Response
import retrofit2.http.*

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
    ): List<CategoriesItem>


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

    @POST("customers")
    suspend fun register(
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions(),
        @Body customer: Customer
    ): Customer

    @GET("products")
    suspend fun searchWithFilter(
        @Query("per_page") page: Int = 55,
        @Query("attribute") attribute : String,
        @Query("attribute_term") attribute_term : String,
        @Query("orderby") orderBy: String,
        @Query("search") search: String,
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions()
    ): List<ProductsApiResultItem>

    @GET("products/attributes/{attribute_id}/terms")
    suspend fun getAttributeTerms(
        @Path("attribute_id") id: Int,
        @Query("per_page") page: Int = 55,
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions()
    ): List<AttributeTermItem>

    @POST("orders")
    suspend fun registerOrder(
        @QueryMap option : Map<String, String> = NetworkParams.getBaseOptions(),
        @Body order: OrderItem
    ): OrderItem


}
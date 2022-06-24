package com.example.mystore.data

import com.example.mystore.data.model.Category
import com.example.mystore.data.model.Image
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.attributeTerm.AttributeTermItem
import com.example.mystore.data.model.category.CategoriesItem
import com.example.mystore.data.model.customer.Customer
import com.example.mystore.data.model.order.OrderItem
import com.example.mystore.data.model.review.ReviewItem
import com.example.mystore.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getProductsOrderBy(order : String): List<ProductsApiResultItem> {
            return apiService.getProductsOrderBy(orderBy = order)
    }

    suspend fun getCategories(): List<CategoriesItem> {
        try {
            return apiService.getCategories()
        } catch (e: Exception) {
            //return sampleCategory()
            return arrayListOf()
        }
    }

    suspend fun getProductsInCategory(category : String): List<ProductsApiResultItem> {
        try {
            return apiService.getProductsInCategory(category = category)
        } catch (e: Exception) {
            //return sampleProducts()
            return arrayListOf()
        }
    }

    suspend fun getSearchedProducts(searchStr : String, order: String): List<ProductsApiResultItem> {
        try {
            return apiService.getSearchedProducts(search = searchStr, orderBy = order)
        } catch (e: Exception) {
            //return sampleProducts()
            return arrayListOf()
        }
    }

    suspend fun getProduct(id : Int): ProductsApiResultItem {
        try {
            return apiService.getProduct(id)
        } catch (e: Exception) {
            return sampleProduct()
        }
    }

    suspend fun getRelatedProducts(str : String): List<ProductsApiResultItem> {
        try {
            return apiService.getRelatedProducts(str = str)
        } catch (e: Exception) {
            //return sampleProducts()
            return arrayListOf()
        }
    }
    suspend fun register(customer: Customer): Customer{
        return apiService.register(customer = customer)
    }

    suspend fun registerOrder(order: OrderItem): OrderItem{
        return apiService.registerOrder(order = order)
    }

    suspend fun searchWithFilter(attribute : String, attribute_term : String, str : String, orderBy : String): List<ProductsApiResultItem> {
        try {
            return apiService.searchWithFilter(attribute = attribute, attribute_term = attribute_term, search = str, orderBy = orderBy)
        } catch (e: Exception) {
            //return sampleProducts()
           return arrayListOf()
        }
    }

    suspend fun getAttributeTerm(id : Int): List<AttributeTermItem> {
        try {
            return apiService.getAttributeTerms(id = id)
        } catch (e: Exception) {
            //return sampleProducts()
            return arrayListOf()
        }
    }

    suspend fun getProductReviews(productId : String): List<ReviewItem> {
        try {
            return apiService.getProductReviews(product_id = productId)
        } catch (e: Exception) {
            return arrayListOf()
        }
    }




    fun sampleProducts() : List<ProductsApiResultItem>{
        val product = ProductsApiResultItem("","","",
        listOf(Category(0, "", "")),"","","",1, listOf(Image(0, "")),"",true,"",
        100,"", listOf(1,2),"","","",100,"","")
        return listOf(product)
    }

    fun sampleProduct() : ProductsApiResultItem{
        val product = ProductsApiResultItem("","","",
            listOf(Category(0, "", "")),"","","",1, listOf(Image(0, "")),"",true,"",
            100,"", listOf(1,2),"","","",100,"","")
        return product
    }

    fun sampleCategory() : List<CategoriesItem>{
        val category = CategoriesItem(5,"","",
            5,5,"category name",1, "")
        return listOf(category)
    }




}
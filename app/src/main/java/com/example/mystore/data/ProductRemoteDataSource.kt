package com.example.mystore.data

import com.example.mystore.data.model.Category
import com.example.mystore.data.model.Image
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesItem
import com.example.mystore.data.model.customer.Customer
import com.example.mystore.data.network.ApiService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getProductsOrderBy(order : String): List<ProductsApiResultItem> {
        try {
            return apiService.getProductsOrderBy(orderBy = order)
        } catch (e: Exception) {
            return sampleProducts()
        }
    }

    suspend fun getCategories(): List<CategoriesItem> {
        try {
            return apiService.getCategories()
        } catch (e: Exception) {
            return sampleCategory()
        }
    }

    suspend fun getProductsInCategory(category : String): List<ProductsApiResultItem> {
        try {
            return apiService.getProductsInCategory(category = category)
        } catch (e: Exception) {
            return sampleProducts()
        }
    }

    suspend fun getSearchedProducts(searchStr : String, order: String): List<ProductsApiResultItem> {
        try {
            return apiService.getSearchedProducts(search = searchStr, orderBy = order)
        } catch (e: Exception) {
            return sampleProducts()
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
            return sampleProducts()
        }
    }
    suspend fun register(customer: Customer): Customer{
        return apiService.register(customer = customer)
    }

    suspend fun searchWithFilter(attribute : String, attribute_term : String, str : String): List<ProductsApiResultItem> {
        try {
            return apiService.searchWithFilter(attribute = attribute, attribute_term = attribute_term, search = str)
        } catch (e: Exception) {
            return sampleProducts()
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
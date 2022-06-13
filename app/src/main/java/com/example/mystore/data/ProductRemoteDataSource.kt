package com.example.mystore.data

import com.example.mystore.data.model.Category
import com.example.mystore.data.model.Image
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesApiResultItem
import com.example.mystore.data.network.ApiService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getProductsOrderBy(order : String): List<ProductsApiResultItem> {
        try {
            return apiService.getProductsOrderBy(orderBy = order)
        } catch (e: Exception) {
            return sampleProduct()
        }
    }

    suspend fun getCategories(): List<CategoriesApiResultItem> {
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
            return sampleProduct()
        }
    }

    suspend fun getSearchedProducts(searchStr : String): List<ProductsApiResultItem> {
        try {
            return apiService.getSearchedProducts(search = searchStr)
        } catch (e: Exception) {
            return sampleProduct()
        }
    }


    fun sampleProduct() : List<ProductsApiResultItem>{
        val product = ProductsApiResultItem("","","",
        listOf(Category(0, "", "")),"","","",1, listOf(Image(0, "")),"",true,"",
        100,"","","","", listOf(),100,"","")
        return listOf(product)
    }

    fun sampleCategory() : List<CategoriesApiResultItem>{
        val category = CategoriesApiResultItem(5,"","",
            5,5,"category name",1, "")
        return listOf(category)
    }


}
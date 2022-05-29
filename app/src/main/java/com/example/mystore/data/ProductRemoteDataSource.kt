package com.example.mystore.data

import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.network.ApiService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {
    suspend fun getNewestProducts(): List<ProductsApiResultItem> {
        try {
            return apiService.getNewestProducts()
        } catch (e: Exception) {
            return sampleProduct()
        }
    }

    fun sampleProduct() : List<ProductsApiResultItem>{
        val product = ProductsApiResultItem("","","",
        "","","",1, listOf(),"",true,"",
        100,"","","",100,"","")
        return listOf(product)
    }
}
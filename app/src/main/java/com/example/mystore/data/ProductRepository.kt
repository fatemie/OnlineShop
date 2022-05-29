package com.example.mystore.data

import com.example.mystore.data.model.ProductsApiResultItem
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {
    suspend fun getNewestProducts(): List<ProductsApiResultItem> {
        return productRemoteDataSource.getNewestProducts()
    }
}
package com.example.mystore.data

import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesApiResultItem
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {

    suspend fun getProductsOrderBy(order : String): List<ProductsApiResultItem> {
        return productRemoteDataSource.getProductsOrderBy(order)
    }

    suspend fun getCategories(): List<CategoriesApiResultItem> {
        return productRemoteDataSource.getCategories()
    }

    suspend fun getProductsInCategory(category : String): List<ProductsApiResultItem> {
        return productRemoteDataSource.getProductsInCategory(category)
    }

    suspend fun getSearchedProducts(searchStr : String, order: String): List<ProductsApiResultItem> {
        return productRemoteDataSource.getSearchedProducts(searchStr, order)
    }

}
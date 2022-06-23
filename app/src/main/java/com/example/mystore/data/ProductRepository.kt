package com.example.mystore.data

import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesItem
import com.example.mystore.data.model.customer.Customer
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {

    suspend fun getProductsOrderBy(order : String): List<ProductsApiResultItem> {
        return productRemoteDataSource.getProductsOrderBy(order)
    }

    suspend fun getCategories(): List<CategoriesItem> {
        return productRemoteDataSource.getCategories()
    }

    suspend fun getProductsInCategory(category : String): List<ProductsApiResultItem> {
        return productRemoteDataSource.getProductsInCategory(category)
    }

    suspend fun getSearchedProducts(searchStr : String, order: String): List<ProductsApiResultItem> {
        return productRemoteDataSource.getSearchedProducts(searchStr, order)
    }

    suspend fun getProduct(id : Int): ProductsApiResultItem {
        return productRemoteDataSource.getProduct(id)
    }

    suspend fun getRelatedProducts(str : String): List<ProductsApiResultItem> {
        return productRemoteDataSource.getRelatedProducts(str)
    }

    suspend fun register(customer: Customer) : Customer {
        return productRemoteDataSource.register(customer)
    }

    suspend fun searchWithFilter(attribute : String, attribute_term : String, str : String): List<ProductsApiResultItem> {
        return productRemoteDataSource.searchWithFilter(attribute, attribute_term, str)
    }

}
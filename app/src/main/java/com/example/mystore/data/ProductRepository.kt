package com.example.mystore.data

import com.example.mystore.data.model.Category
import com.example.mystore.data.model.Image
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.attributeTerm.AttributeTermItem
import com.example.mystore.data.model.category.CategoriesItem
import com.example.mystore.data.model.customer.Customer
import com.example.mystore.data.model.order.OrderItem
import com.example.mystore.data.model.review.ReviewItem
import com.example.mystore.data.model.review.reviewForServer
import retrofit2.Response
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
        return  productRemoteDataSource.register(customer)
    }

    suspend fun registerOrder(order: OrderItem) : OrderItem {
        return productRemoteDataSource.registerOrder(order)
    }

    suspend fun searchWithFilter(attribute : String, attribute_term : String, str : String, orderBy: String): List<ProductsApiResultItem> {
        return productRemoteDataSource.searchWithFilter(attribute, attribute_term, str,orderBy )
    }

    suspend fun getAttributeTerms(attribute_id : Int): List<AttributeTermItem> {
        return productRemoteDataSource.getAttributeTerm(attribute_id)
    }

    suspend fun getProductReviews(product_id : Int): List<ReviewItem> {
        return productRemoteDataSource.getProductReviews(product_id)
    }

    suspend fun createReview(review : ReviewItem): ReviewItem {
        return productRemoteDataSource.createReview(review)
    }

    suspend fun getReviewById(id : Int): ReviewItem {
        return productRemoteDataSource.getReviewById(id)
    }

    suspend fun updateReview(id : Int,review: reviewForServer): reviewForServer {
        return productRemoteDataSource.updateReview(id, review)
    }

    suspend fun deleteReview(id : Int): ReviewItem {
        return productRemoteDataSource.deleteReview(id)
    }


}
package com.example.mystore.data

import com.example.mystore.data.model.Category
import com.example.mystore.data.model.Image
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.attributeTerm.AttributeTermItem
import com.example.mystore.data.model.category.CategoriesItem
import com.example.mystore.data.model.customer.Billing
import com.example.mystore.data.model.customer.Customer
import com.example.mystore.data.model.order.OrderItem
import com.example.mystore.data.model.review.ReviewItem
import com.example.mystore.data.network.ApiService
import com.example.mystore.ui.customerRegisterOK
import com.example.mystore.ui.errorException
import com.example.mystore.ui.orderRegisterOK
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getProductsOrderBy(order: String): List<ProductsApiResultItem> {
        return try {
            errorException.value = null
            apiService.getProductsOrderBy(orderBy = order)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            listOf()
        }
    }

    suspend fun getCategories(): List<CategoriesItem> {
        return try {
            errorException.value = null
            apiService.getCategories()
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            listOf()
        }
    }

    suspend fun getProductsInCategory(category: String): List<ProductsApiResultItem> {
        return try {
            errorException.value = null
            apiService.getProductsInCategory(category = category)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            listOf()
        }
    }

    suspend fun getSearchedProducts(searchStr: String, order: String): List<ProductsApiResultItem> {
        return try {
            errorException.value = null
            apiService.getSearchedProducts(search = searchStr, orderBy = order)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            listOf()
        }
    }

    suspend fun getProduct(id: Int): ProductsApiResultItem {
        return try {
            errorException.value = null
            apiService.getProduct(id)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            sampleProduct()
        }
    }

    suspend fun getRelatedProducts(str: String): List<ProductsApiResultItem> {
        return try {
            errorException.value = null
            apiService.getRelatedProducts(str = str)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            listOf()
        }
    }

    suspend fun register(customer: Customer): Customer {
        customerRegisterOK = true
        return try {
            errorException.value = null
            apiService.register(customer = customer)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            customerRegisterOK = false
            sampleCustomer()
        }
    }

    suspend fun registerOrder(order: OrderItem): OrderItem {
        orderRegisterOK = true
        return try{
            errorException.value = null
            apiService.registerOrder(order = order)
        } catch (e: Exception){
            if (errorException.value == null)
                errorException.value = e
            orderRegisterOK = false
            sampleOrder()
        }
    }

    suspend fun searchWithFilter(
        attribute: String,
        attribute_term: String,
        str: String,
        orderBy: String
    ): List<ProductsApiResultItem> {
        return try {
            errorException.value = null
            apiService.searchWithFilter(
                attribute = attribute,
                attribute_term = attribute_term,
                search = str,
                orderBy = orderBy)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            listOf()
        }
    }

    suspend fun getAttributeTerm(id: Int): List<AttributeTermItem> {
        return try {
            errorException.value = null
            apiService.getAttributeTerms(id = id)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            listOf()
        }
    }

    suspend fun getProductReviews(product_id: Int): List<ReviewItem> {
        return try {
            errorException.value = null
            apiService.getProductReviews(product_id = product_id)
        } catch (e: Exception) {
            if (errorException.value == null)
                errorException.value = e
            listOf()
        }
    }

    fun sampleProduct(): ProductsApiResultItem {
        val product = ProductsApiResultItem(
            "", "", "",
            listOf(Category(0, "", "")), "", "", "", 1, listOf(Image(0, "")), "", true, "",
            100, "", listOf(1, 2), "", "", "", 100, "", ""
        )
        return product
    }

    fun sampleCustomer() : Customer{
        val customer = Customer("email", "firstName", "lastName",
        Billing("address1", "address2","tehran", "company", "Iran",
        "email", "firstName", "lastName", "phone", "postCode", "state")
        )
        return customer
    }

    fun sampleOrder() : OrderItem{
        val order = OrderItem(Billing("address1", "address2","tehran", "company", "Iran",
            "email", "firstName", "lastName", "phone", "postCode", "state"), listOf())
    return order}

}
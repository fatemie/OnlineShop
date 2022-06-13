package com.example.mystore.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepository , app: Application) : AndroidViewModel(app) {
    val productList = MutableLiveData<List<ProductsApiResultItem>>()
    val mostPopularProduct = MutableLiveData<List<ProductsApiResultItem>>()
    val mostViewProduct = MutableLiveData<List<ProductsApiResultItem>>()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {

            val dateList = repository.getProductsOrderBy("date")
            productList.value = dateList
            val popularList = repository.getProductsOrderBy("popularity")
            mostPopularProduct.value = popularList
            val mostViewList = repository.getProductsOrderBy("rating")
            mostViewProduct.value = mostViewList
        }
    }


}
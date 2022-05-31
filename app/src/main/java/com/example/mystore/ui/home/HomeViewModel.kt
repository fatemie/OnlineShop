package com.example.mystore.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {
    val productList = MutableLiveData<List<ProductsApiResultItem>>()
    val mostPopularProduct = MutableLiveData<List<ProductsApiResultItem>>()
    val mostViewProduct = MutableLiveData<List<ProductsApiResultItem>>()

    init {
        getNewestProducts()
    }

    private fun getNewestProducts() {
        viewModelScope.launch {
            val list = repository.getNewestProducts()
            productList.value = list
            val popularList = list.sortedBy { it.averageRating }
            mostPopularProduct.value = popularList
            val mostViewList = list.sortedBy { it.ratingCount }
            mostViewProduct.value = mostViewList
        }
    }


}
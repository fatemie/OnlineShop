package com.example.mystore.ui.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystor.databinding.FragmentProductDetailBinding
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.productDetail.ProductDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {

    val products = MutableLiveData<List<ProductsApiResultItem>>()
    val searchList = MutableLiveData<List<ProductsApiResultItem>>()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            val list = repository.getProductsOrderBy("date ")
            products.value = list
        }
    }

    fun searchProducts(str : String){
        searchList.value = listOf()
        val arrayList = arrayListOf<ProductsApiResultItem>()
        for (product in products.value!!){
            if(product.name.contains(" "+str+" ") || product.description.contains(" "+str+" ")){
                arrayList.add(product)
            }
        }
        searchList.value = arrayList
    }


}
package com.example.mystore.ui.productDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {

    val product = MutableLiveData<ProductsApiResultItem>()

    fun getProduct(id : Int)  {
        viewModelScope.launch {
            val list = repository.getNewestProducts()
            for ( thisProduct in list){
                if(thisProduct.id == id)
                    product.value = thisProduct
            }
        }
    }
}
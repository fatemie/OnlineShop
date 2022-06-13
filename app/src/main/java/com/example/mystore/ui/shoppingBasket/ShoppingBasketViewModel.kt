package com.example.mystore.ui.shoppingBasket

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
class ShoppingBasketViewModel @Inject constructor(private val repository : ProductRepository, app: Application) : AndroidViewModel(app) {
    val ShoppingBasketList = MutableLiveData<ArrayList<ProductsApiResultItem>>()

    fun addProductToBasket(productId : Int){
        viewModelScope.launch {
            val product = repository.getProduct(productId)
            ShoppingBasketList.value?.add(product)
        }
    }

}
package com.example.mystore.ui.shoppingBasket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingBasketViewModel @Inject constructor(
    private val repository: ProductRepository,
    app: Application
) : AndroidViewModel(app) {
    val shoppingBasketList = MutableLiveData<List<ProductsApiResultItem>>()
    val arrayList = arrayListOf<ProductsApiResultItem>()
    val totalPrice = MutableLiveData<String>()


    fun addProductToBasket(productId: Int) {
        viewModelScope.launch {
            val product = repository.getProduct(productId)
            if (!arrayList.contains(product)) {
                arrayList.add(product)
                shoppingBasketList.value = arrayList
            } else {
                product.numberInBasket++
            }
            totalPrice.value = calculateTotalPrice().toString()
        }

    }

    fun calculateTotalPrice(): Int {
        var totalPrice = 0
        for (product in arrayList) {
            totalPrice = totalPrice + (product.numberInBasket * product.price.toInt())
        }
        return totalPrice
    }

}
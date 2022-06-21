package com.example.mystore.ui.shoppingBasket

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mystor.R
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PRODUCTSINBASKET = "PRODUCTSINBASKET"

@HiltViewModel
class ShoppingBasketViewModel @Inject constructor(
    private val repository: ProductRepository,
    val app: Application
) : AndroidViewModel(app) {
    val shoppingBasketList = MutableLiveData<List<ProductsApiResultItem>>()
    var arrayList = arrayListOf<ProductsApiResultItem>()
    val totalPrice = MutableLiveData<String>()
    lateinit var prefs: SharedPreferences
    var productStr = ""

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

    fun onProductChanged(productId: Int) {
        var index = 0
        viewModelScope.launch {
            val product = repository.getProduct(productId)
            for (i in 0..arrayList.size - 1) {
                if (arrayList[i].id == product.id) {
                    index = i
                    if (arrayList[i].numberInBasket == 0) {
                        arrayList.removeAt(index)
                        shoppingBasketList.value = arrayList
                    }
                }
                productStr =
                    productStr + "${arrayList[i].id.toString() + "/" + arrayList[i].numberInBasket.toString()}" + " "
            }
        }
        totalPrice.value = calculateTotalPrice().toString()
        saveBasketInSharedPref()
    }


    fun calculateTotalPrice(): Int {
        var totalPrice = 0
        for (product in arrayList) {
            totalPrice = totalPrice + (product.numberInBasket * product.price.toInt())
        }
        return totalPrice
    }

    fun saveBasketInSharedPref() {
        prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()

        editor.putString(PRODUCTSINBASKET, productStr)
        editor.apply()
    }

}
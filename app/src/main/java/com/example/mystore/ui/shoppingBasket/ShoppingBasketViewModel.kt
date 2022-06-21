package com.example.mystore.ui.shoppingBasket

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
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
    lateinit var prefs: SharedPreferences
    val shoppingBasketList = MutableLiveData<List<ProductsApiResultItem>>()
    var arrayList = arrayListOf<ProductsApiResultItem>()
    val totalPrice = MutableLiveData<String>()
    var productStr = ""

    init {
        readProductsFromSharedPref()
    }

    fun addProductToBasket(productId: Int, number: Int) {

        viewModelScope.launch {
            val product = repository.getProduct(productId)
            arrayList.add(product)
            shoppingBasketList.value = arrayList

            if (number == 0) {
                product.numberInBasket++
            } else {
                product.numberInBasket = number
            }
            totalPrice.value = calculateTotalPrice().toString()
        }
    }

    fun onProductChanged(productId: Int) {

        var index = 0
        productStr = ""
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
            Log.e("salam", productStr)
            saveBasketInSharedPref(productStr)
        }
        totalPrice.value = calculateTotalPrice().toString()
    }


    private fun calculateTotalPrice(): Int {
        var totalPrice = 0
        for (product in arrayList) {
            totalPrice += (product.numberInBasket * product.price.toInt())
        }
        return totalPrice
    }

    private fun saveBasketInSharedPref(str: String) {
        prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("boolean", false)
        editor.putString(PRODUCTSINBASKET, str)
        editor.apply()
        val productsListStr = prefs.getString(PRODUCTSINBASKET, "").toString()
        Log.e("salamRead", productsListStr)
    }

    fun readProductsFromSharedPref() {
        val prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val productsListStr = prefs.getString(PRODUCTSINBASKET, "").toString()


        if (arrayList.isEmpty()) {
            val list = productsListStr.split(" ")
            //Log.e("salamSplit ", list.toString())
            for (item in list) {
                if (item.contains("/")) {
                    val product = item.split("/")
                    //Log.e("salamSplit/", product.toString())
                    val productId = Integer.parseInt(product[0])
                    //Log.e("salamID", productId.toString())
                    val number = Integer.parseInt(product[1])
                    //Log.e("salamNumber", number.toString())
                    addProductToBasket(productId, number)
                }

            }
        }
    }

}
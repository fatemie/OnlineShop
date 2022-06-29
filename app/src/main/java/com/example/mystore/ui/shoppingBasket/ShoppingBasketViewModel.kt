package com.example.mystore.ui.shoppingBasket

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mystor.R
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.customer.Billing
import com.example.mystore.data.model.order.LineItem
import com.example.mystore.data.model.order.OrderItem
import com.example.mystore.ui.login.*
import com.example.mystore.ui.orderRegisterOK
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDate.now
import javax.inject.Inject

const val PRODUCTSINBASKET = "PRODUCTSINBASKET"

@HiltViewModel
class ShoppingBasketViewModel @Inject constructor(
    private val repository: ProductRepository,
    val app: Application
) : AndroidViewModel(app) {
    lateinit var prefs: SharedPreferences
    val shoppingBasketList = MutableLiveData<List<ProductsApiResultItem>>()
    private var arrayList = arrayListOf<ProductsApiResultItem>()
    val totalPrice = MutableLiveData<String>("0")
    var productStr = ""
    var basketIsEmpty = MutableLiveData<Boolean>()

    init {
        readProductsFromSharedPref()
    }

    fun addProductToBasket(productId: Int, number: Int) {
        productStr = ""
        viewModelScope.launch {
            val product = repository.getProduct(productId)
            arrayList.add(product)
            shoppingBasketList.value = arrayList

            if (number == 0) {
                product.numberInBasket++
            } else {
                product.numberInBasket = number
            }
            onProductChanged(product)
            totalPrice.value = calculateTotalPrice().toString()
        }
    }

    fun onProductChanged(product: ProductsApiResultItem) {
        productStr = ""
        var index: Int

        for (i in 0..arrayList.size - 1) {
            if (arrayList[i].id == product.id) {
                index = i
                if (arrayList[i].numberInBasket == 0) {
                    arrayList.removeAt(index)
                    shoppingBasketList.value = arrayList
                    continue
                }
            }
            productStr += arrayList[i].id.toString() + "/" + arrayList[i].numberInBasket.toString() + " "
        }
        Log.e("tagChange", productStr)
        saveBasketInSharedPref()
        totalPrice.value = calculateTotalPrice().toString()
        basketIsEmpty.value = arrayList.isEmpty()
    }


    private fun calculateTotalPrice(): Int {
        var totalPrice = 0
        for (product in arrayList) {
            totalPrice += (product.numberInBasket * product.price.toInt())
        }
        return totalPrice
    }

    private fun saveBasketInSharedPref() {
        prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString(PRODUCTSINBASKET, productStr)
        editor.apply()
    }

    private fun readProductsFromSharedPref() {
        val prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val productsListStr = prefs.getString(PRODUCTSINBASKET, "").toString()
        if (arrayList.isEmpty() && productsListStr.isNotEmpty()) {
            val list = productsListStr.split(" ")
            for (item in list) {
                if (item.contains("/")) {
                    val product = item.split("/")
                    val productId = Integer.parseInt(product[0])
                    val number = Integer.parseInt(product[1])
                    addProductToBasket(productId, number)
                }
            }
            basketIsEmpty.value = false
        }
    }

    fun basketIsEmpty(): Boolean {
        val prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val productsListStr = prefs.getString(PRODUCTSINBASKET, "").toString()
        basketIsEmpty.value = productsListStr.isBlank()
        return (productsListStr.isBlank())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerBasket(view : View) {
        val prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )

        viewModelScope.launch {
            val address = prefs.getString(ADDRESS, "").toString()
            val email = prefs.getString(EMAIL, "").toString()
            val firstName = prefs.getString(FIRSTNAME, "").toString()
            val lastName = prefs.getString(LASTNAME, "").toString()
            val phone = prefs.getString(PHONE, "").toString()
            val postCode = prefs.getString(POSTALCODE, "").toString()
            val newArray = arrayListOf<LineItem>()
            Log.e("tag", "salam")
            for (product in arrayList) {
                val lineItem = LineItem(
                    0,
                    product.name,
                    product.price.toInt(),
                    product.id,
                    product.numberInBasket
                )
                newArray.add(lineItem)
            }
            val order = OrderItem(
                Billing(
                    address, "", "tehran", "",
                    "Iran", email, firstName, lastName, phone, postCode, "",
                ),
                newArray
            )
            Log.e("tagEmail", email)
            repository.registerOrder(order)
            if (orderRegisterOK){
                basketIsRegistered(view)
            }

        }

    }

    fun basketIsRegistered(view : View){
        val snack = Snackbar.make(view,"اطلاعات با موفقیت افزوده شد",
            Snackbar.LENGTH_LONG)
        snack.show()
        productStr = ""
        arrayList = arrayListOf()
        shoppingBasketList.value = arrayList
        basketIsEmpty.value = true
        saveBasketInSharedPref()
        totalPrice.value = "0"

    }

    fun isLoggedIn(): Boolean {
        val prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val customerName = prefs.getString(FIRSTNAME, "").toString()
        Log.e("tag", customerName)
        return (customerName.isNotEmpty())
    }


}
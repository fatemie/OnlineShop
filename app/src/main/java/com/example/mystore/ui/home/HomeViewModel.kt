package com.example.mystore.ui.home

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepository , val app: Application) : AndroidViewModel(app) {
    val productList = MutableLiveData<List<ProductsApiResultItem>>()
    val mostPopularProduct = MutableLiveData<List<ProductsApiResultItem>>()
    val mostViewProduct = MutableLiveData<List<ProductsApiResultItem>>()
    val specialProducts = MutableLiveData<ProductsApiResultItem>()


    fun getProducts() {
        var array = arrayListOf<ProductsApiResultItem>()
        viewModelScope.launch {
            val dateList = repository.getProductsOrderBy("date")
            specialProducts.value = dateList[10]
            array = dateList as ArrayList
            array.removeAt(10)
            productList.value = array
            val popularList = repository.getProductsOrderBy("popularity")
            mostPopularProduct.value = popularList
            val mostViewList = repository.getProductsOrderBy("rating")
            mostViewProduct.value = mostViewList
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(): Boolean {
        val connectivityManager =
            app.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


}
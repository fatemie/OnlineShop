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

    init {
        getProducts()
    }

    fun getProducts() {
        var array = arrayListOf<ProductsApiResultItem>()
        viewModelScope.launch {
//            productList.postValue(Resource.Loading())
//            try {
//                val response  = repository.getProductsOrderBy("date")
//                productList.postValue(handleProductList(response))
//                specialProducts.value = productList.value?.data?.get(10)
//                array = productList.value?.data as ArrayList
//                array.removeAt(10)
//                productList.value!!.data = array
//
//                val response1  = repository.getProductsOrderBy("popularity")
//                mostPopularProduct.postValue(handleProductList(response1))
//                val response2  = repository.getProductsOrderBy("rating")
//                mostViewProduct.postValue(handleProductList(response2))
//
//            }catch (t :Throwable){
//                productList.postValue(Resource.Error("Unknown Error!"))
//            }
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

    private fun handleProductList(response: Response<List<ProductsApiResultItem>>):Resource<List<ProductsApiResultItem>>{
        if (response.isSuccessful){
            response.body()?.let{
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }



}
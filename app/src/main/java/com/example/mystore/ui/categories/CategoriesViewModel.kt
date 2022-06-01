package com.example.mystore.ui.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {
    val productsInCategory = MutableLiveData<List<ProductsApiResultItem>>()


    fun getProductsInCategories(category : String) {
        viewModelScope.launch {
            val list = repository.getNewestProducts()
            val array = arrayListOf<ProductsApiResultItem>()
            for (product in list){
                if (product.categories[0].name == category){
                    array.add(product)
                }
            }
            productsInCategory.value = array.toList()
        }
    }
}
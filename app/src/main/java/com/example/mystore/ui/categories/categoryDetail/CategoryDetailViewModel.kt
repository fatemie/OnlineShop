package com.example.mystore.ui.categories.categoryDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel(){

    val productsInCategory = MutableLiveData<List<ProductsApiResultItem>>()

    fun getProductsInCategory(category_id : String) {
        viewModelScope.launch {
            var list = repository.getProductsInCategory(category_id)
            productsInCategory.value = list
        }
    }

}
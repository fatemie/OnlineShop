package com.example.mystore.ui.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {
    val categories = MutableLiveData<List<CategoriesItem>>()
    val productsInCategoryHealth = MutableLiveData<List<ProductsApiResultItem>>()
    val productsInCategoryWomenClothing = MutableLiveData<List<ProductsApiResultItem>>()
    val productsInCategoryMenClothing = MutableLiveData<List<ProductsApiResultItem>>()
    val productsInCategoryDigitals = MutableLiveData<List<ProductsApiResultItem>>()
    val productsInCategoryClocks = MutableLiveData<List<ProductsApiResultItem>>()
    val productsInCategorySuperMarket = MutableLiveData<List<ProductsApiResultItem>>()

    init {
        getCategories()
    }


    private fun getCategories() {
        viewModelScope.launch {
            val list = repository.getCategories()
            categories.value = list
            var list1 = repository.getProductsInCategory("121")
            productsInCategoryHealth.value = list1
            list1 = repository.getProductsInCategory("63")
            productsInCategoryWomenClothing.value = list1
            list1 = repository.getProductsInCategory("64")
            productsInCategoryMenClothing.value = list1
            list1 = repository.getProductsInCategory("52")
            productsInCategoryDigitals.value = list1
            list1 = repository.getProductsInCategory("102")
            productsInCategoryClocks.value = list1
            list1 = repository.getProductsInCategory("81")
            productsInCategorySuperMarket.value = list1

        }
    }
}
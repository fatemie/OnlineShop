package com.example.mystore.ui.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystor.databinding.FragmentProductDetailBinding
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.productDetail.ProductDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {

    val searchList = MutableLiveData<List<ProductsApiResultItem>>()

    fun getSearchedProducts(searchStr : String, order : String) {
        viewModelScope.launch {
            val list = repository.getSearchedProducts(searchStr, order)
            searchList.value = list
        }
    }

    fun searchWithFilter(attribute : String, attribute_term : String){
        viewModelScope.launch {
            val list = repository.searchWithFilter(attribute, attribute_term)
            searchList.value = list
        }
    }


}
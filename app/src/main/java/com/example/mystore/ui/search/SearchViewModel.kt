package com.example.mystore.ui.search

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystor.databinding.FragmentProductDetailBinding
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.attributeTerm.AttributeTermItem
import com.example.mystore.ui.productDetail.ProductDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {

    val searchList = MutableLiveData<List<ProductsApiResultItem>>()
    val colorTerms = MutableLiveData<List<AttributeTermItem>>()
    val sizeTerms = MutableLiveData<List<AttributeTermItem>>()
    var colorTermId : String = ""
    var sizeTermId : String = ""

    init {
        getColorTerms()
        getSizeTerms()
    }

    fun getSearchedProducts(searchStr : String, order : String) {
        viewModelScope.launch {
            val list = repository.getSearchedProducts(searchStr, order)
            searchList.value = list
        }
    }

    fun searchWithFilter(attribute : String, attribute_term : String, str : String, orderBy : String){
        viewModelScope.launch {
            val list = repository.searchWithFilter(attribute, attribute_term, str, orderBy)
            searchList.value = list
        }
    }

    fun getColorTerms(){
        viewModelScope.launch {
            val list = repository.getAttributeTerms(3)
            colorTerms.value = list
            //Log.e("tag", list[0].name)
        }
    }

    fun getSizeTerms(){
        viewModelScope.launch {
            val list = repository.getAttributeTerms(4)
           sizeTerms.value = list
        }
    }









}
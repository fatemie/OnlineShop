package com.example.mystore.ui.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.mystor.databinding.FragmentProductDetailBinding
import com.example.mystore.data.ProductRepository
import com.example.mystore.ui.productDetail.ProductDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {

}
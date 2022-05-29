package com.example.mystore.ui.productDetail

import androidx.lifecycle.ViewModel
import com.example.mystore.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel  @Inject constructor(private val repository : ProductRepository) : ViewModel() {
}
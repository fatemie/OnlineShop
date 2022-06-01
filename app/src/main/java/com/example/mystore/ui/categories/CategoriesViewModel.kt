package com.example.mystore.ui.categories

import androidx.lifecycle.ViewModel
import com.example.mystore.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel() {
}
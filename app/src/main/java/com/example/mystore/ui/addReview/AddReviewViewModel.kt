package com.example.mystore.ui.addReview

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.review.ReviewItem
import com.example.mystore.ui.customerRegisterOK
import com.example.mystore.ui.reviewRegisterOK
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel(){

    fun createReview(review : ReviewItem, view : View){
        viewModelScope.launch {
            repository.createReview(review)
            if(reviewRegisterOK){
                val snack = Snackbar.make(view,"نظر شما با موفقیت افزوده شد",
                    Snackbar.LENGTH_LONG)
                snack.show()
                reviewRegisterOK = false
            }
        }
    }
}
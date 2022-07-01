package com.example.mystore.ui.addReview

import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.review.ReviewItem
import com.example.mystore.data.model.review.reviewForServer
import com.example.mystore.ui.customerRegisterOK
import com.example.mystore.ui.errorException
import com.example.mystore.ui.reviewRegisterOK
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(private val repository : ProductRepository) : ViewModel(){
    val thisReview = MutableLiveData<ReviewItem>()
    val reviewIsDeleted = MutableLiveData<Boolean>(false)

    fun createReview(review : ReviewItem, view : View){
        viewModelScope.launch {
            repository.createReview(review)
            if(reviewRegisterOK){
                val snack = Snackbar.make(view,"نظر شما با موفقیت افزوده شد",
                    Snackbar.LENGTH_LONG)
                snack.show()
            }
        }
    }

    fun getReviewById(id : Int){
        viewModelScope.launch {
            val review = repository.getReviewById(id)
            val newDescription = review.reviewDescription.replace("<br />", "")
                .replace("<p>", "")
                .replace("</p>", "")
            review.reviewDescription = newDescription
            thisReview.value = review
        }
    }

    fun updateReview(id : Int,review: reviewForServer){
        viewModelScope.launch {
            repository.updateReview(id,review)
        }
    }

    fun deleteReview(id : Int, view: View){
        viewModelScope.launch {
            repository.deleteReview(id)
            if(reviewRegisterOK){
                val snack = Snackbar.make(view,"نظر شما با موفقیت حذف شد",
                    Snackbar.LENGTH_LONG)
                snack.show()
                reviewIsDeleted.value = true
            }
        }
    }
}
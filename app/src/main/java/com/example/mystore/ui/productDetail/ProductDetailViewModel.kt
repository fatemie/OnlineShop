package com.example.mystore.ui.productDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.review.ReviewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    val product = MutableLiveData<ProductsApiResultItem>()
    val description = MutableLiveData<String>()
    val relatedProducts = MutableLiveData<List<ProductsApiResultItem>>()
    val reviews = MutableLiveData<List<ReviewItem>>()


    fun getProduct(id: Int) {
        viewModelScope.launch {
            val thisProduct = repository.getProduct(id)
            product.value = thisProduct
            description.value = thisProduct.description.replace("<br />", "")
                .replace("<p>", "")
                .replace("</p>", "")
            getRelatedProducts(thisProduct.relatedIds.toString() )
        }
    }

    fun getRelatedProducts(str : String) {
        viewModelScope.launch {
            val thisRelatedProducts = repository.getRelatedProducts(str)
            relatedProducts.value = thisRelatedProducts
        }
    }

    fun getProductReviews(productId : String){
        viewModelScope.launch {
            val thisProductReviews = repository.getProductReviews(productId)
            reviews.value = thisProductReviews
        }
    }

}


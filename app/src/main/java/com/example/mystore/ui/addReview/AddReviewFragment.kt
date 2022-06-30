package com.example.mystore.ui.addReview

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.mystor.R
import com.example.mystor.databinding.FragmentAddReviewBinding
import com.example.mystore.data.model.review.ReviewItem
import com.example.mystore.data.model.review.ReviewerAvatarUrls
import com.example.mystore.ui.BaseFragment
import com.example.mystore.ui.login.EMAIL
import com.example.mystore.ui.login.FIRSTNAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddReviewFragment : BaseFragment() {

    private val vModel: AddReviewViewModel by viewModels()
    lateinit var binding : FragmentAddReviewBinding
    lateinit var prefs: SharedPreferences


    private var productID = 0
    private var reviewID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productID = it.getInt("productId")
            reviewID = it.getInt("reviewId")
        }
        if(reviewID != 0){
            vModel.getReviewById(reviewID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (reviewID != 0){
            vModel.thisReview.observe(viewLifecycleOwner){
                binding.TextFieldName.editText!!.setText(it.reviewer)
                binding.TextFieldReview.editText!!.setText(it.reviewDescription)
                binding.seekBar.setProgress(it.rating)
            }
        }

        binding.btnRegister.setOnClickListener {
            createReview()
        }
    }

    fun createReview(){
        val prefs = activity?.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val email = prefs!!.getString(EMAIL, "").toString()

        val review = ReviewItem(productID, binding.seekBar.progress, binding.TextFieldName.editText!!.text.toString(),
            email,binding.TextFieldReview.editText!!.text.toString(), ReviewerAvatarUrls("","",""),0)

        vModel.createReview(review,binding.btnRegister)
    }

}
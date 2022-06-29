package com.example.mystore.ui.addReview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mystor.R
import com.example.mystor.databinding.FragmentAddReviewBinding
import com.example.mystor.databinding.FragmentCategoryDetailBinding
import com.example.mystore.ui.categories.categoryDetail.CategoryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddReviewFragment : Fragment() {

    private val vModel: AddReviewViewModel by viewModels()
    lateinit var binding : FragmentAddReviewBinding

    private var productID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productID = it.getInt("productId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        return  binding.root

    }

}
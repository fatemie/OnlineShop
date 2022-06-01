package com.example.mystore.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mystor.R
import com.example.mystor.databinding.FragmentCategoriesBinding
import com.example.mystor.databinding.FragmentHomeBinding
import com.example.mystore.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private val vModel: CategoriesViewModel by viewModels()
    lateinit var binding: FragmentCategoriesBinding
    lateinit var categories: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categories = it.getString("categories").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        binding.vModel = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

}
package com.example.mystore.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.mystor.R
import com.example.mystor.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    val vModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding

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
package com.example.mystore.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentProductDetailBinding
import com.example.mystor.databinding.FragmentSearchBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.adapter.SearchAdapter
import com.example.mystore.ui.categories.CategoriesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val vModel: SearchViewModel by viewModels()
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search, container, false
        )
        binding.vModel = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        onRadioButtonClicked(binding.category)

        val searchAdapter = SearchAdapter { product -> goToProductDetailFragment(product) }
        binding.rvSearchList.adapter = searchAdapter
        vModel.searchList.observe(viewLifecycleOwner) { searchAdapter.submitList(it) }
    }

    fun setListener(){
        binding.btnSearch.setOnClickListener {
            vModel.searchProducts(binding.etSearch.text.toString())
        }
    }

    fun goToProductDetailFragment(product : ProductsApiResultItem){
        val action =
            SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product.id)
        findNavController().navigate(action)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

//            // Check which radio button was clicked
//            when (view.getId()) {
//                R.id.health ->
//                    if (checked) {
//                        binding.healthDetail.isVisible = true
//                    }
//                R.id.bags ->
//                    if (checked) {
//                        // Ninjas rule
//                    }
//            }
        }
    }

}
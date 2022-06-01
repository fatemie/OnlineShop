package com.example.mystore.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentCategoriesBinding
import com.example.mystor.databinding.FragmentHomeBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.adapter.CategoriesAdapter
import com.example.mystore.ui.adapter.NewestProductAdapter
import com.example.mystore.ui.home.HomeFragmentDirections
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
            R.layout.fragment_categories, container, false
        )
        binding.vModel = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.getProductsInCategories(categories)

        val categoryAdapter = CategoriesAdapter { product -> goToProductDetailFragment(product) }
        binding.categoriesRecyclerView.adapter = categoryAdapter
        vModel.productsInCategory.observe(viewLifecycleOwner) { categoryAdapter.submitList(it) }
    }

    fun goToProductDetailFragment(product : ProductsApiResultItem){
        val action =
            CategoriesFragmentDirections.actionCategoriesFragmentToProductDetailFragment(product.id)
        findNavController().navigate(action)
    }

}
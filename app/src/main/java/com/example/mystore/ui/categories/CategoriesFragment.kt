package com.example.mystore.ui.categories

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentCategoriesBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.category.CategoriesItem
import com.example.mystore.ui.BaseFragment
import com.example.mystore.ui.adapter.CategoriesAdapter
import com.example.mystore.ui.adapter.ProductsAdapter
import com.example.mystore.ui.home.HomeViewModel
import com.example.mystore.ui.shoppingBasket.ShoppingBasketViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : BaseFragment() {
    private val vModel: CategoriesViewModel by viewModels()
    private val homeVModel: HomeViewModel by activityViewModels()

    lateinit var binding: FragmentCategoriesBinding
    lateinit var categories: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categories = it.getString("categories").toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!homeVModel.isOnline()) {
            binding.llCategories.visibility = View.INVISIBLE
            val snack = Snackbar.make(binding.llCategories,"خطا در برقراری ارتباط",Snackbar.LENGTH_LONG)
            snack.show()
        }

        val mainCategoryAdapter = CategoriesAdapter { category -> goToCategoryDetailFragment(category) }
        binding.categoriesRecyclerView.adapter = mainCategoryAdapter
        vModel.categories.observe(viewLifecycleOwner){
            mainCategoryAdapter.submitList(it)
            binding.loadingAnimation.visibility = View.GONE
            binding.mainLayout.visibility = View.VISIBLE}

    }

    private fun goToCategoryDetailFragment(category: CategoriesItem) {
        val action =
            CategoriesFragmentDirections.actionCategoriesFragmentToCategoryDetailFragment(category.id.toString())
        findNavController().navigate(action)
    }


}
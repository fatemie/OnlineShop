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
import com.example.mystore.ui.adapter.ProductsAdapter
import com.example.mystore.ui.home.HomeViewModel
import com.example.mystore.ui.shoppingBasket.ShoppingBasketViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
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

        val healthCategoryAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.category1RecyclerView.adapter = healthCategoryAdapter
        vModel.productsInCategoryHealth.observe(viewLifecycleOwner){healthCategoryAdapter.submitList(it)}

        val womenClothingCategoryAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.category2RecyclerView.adapter = womenClothingCategoryAdapter
        vModel.productsInCategoryWomenClothing.observe(viewLifecycleOwner){womenClothingCategoryAdapter.submitList(it)}

        val menClothingCategoryAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.category3RecyclerView.adapter = menClothingCategoryAdapter
        vModel.productsInCategoryMenClothing.observe(viewLifecycleOwner){menClothingCategoryAdapter.submitList(it)}

        val digitalsCategoryAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.category4RecyclerView.adapter = digitalsCategoryAdapter
        vModel.productsInCategoryDigitals.observe(viewLifecycleOwner){digitalsCategoryAdapter.submitList(it)}

        val clocksCategoryAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.category5RecyclerView.adapter = clocksCategoryAdapter
        vModel.productsInCategoryClocks.observe(viewLifecycleOwner){clocksCategoryAdapter.submitList(it)}

        val superMarketCategoryAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.category6RecyclerView.adapter = superMarketCategoryAdapter
        vModel.productsInCategorySuperMarket.observe(viewLifecycleOwner){superMarketCategoryAdapter.submitList(it)}

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun goToProductDetailFragment(product: ProductsApiResultItem) {
        if(homeVModel.isOnline()) {
            val action =
                CategoriesFragmentDirections.actionCategoriesFragmentToProductDetailFragment(product.id)
            findNavController().navigate(action)
        }else{
            val snack = Snackbar.make(binding.category1RecyclerView,"خطا در برقراری ارتباط",
                Snackbar.LENGTH_LONG)
            snack.show()
        }
    }

}
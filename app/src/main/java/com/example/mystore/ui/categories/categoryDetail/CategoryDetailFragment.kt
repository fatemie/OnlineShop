package com.example.mystore.ui.categories.categoryDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentCategoryDetailBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.adapter.ProductsAdapter
import com.example.mystore.ui.categories.CategoriesFragmentDirections
import com.example.mystore.ui.categories.CategoriesViewModel
import com.example.mystore.ui.shoppingBasket.ShoppingBasketFragmentDirections
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class CategoryDetailFragment : Fragment() {

    private val vModel: CategoryDetailViewModel by viewModels()
    lateinit var binding : FragmentCategoryDetailBinding
    var categoryId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getString("categoryID").toString()
            vModel.getProductsInCategory(categoryId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_category_detail, container, false
        )
        binding.vModel = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryDetailAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.categoryDetailRecyclerView.adapter = categoryDetailAdapter
        vModel.productsInCategory.observe(viewLifecycleOwner) {
            categoryDetailAdapter.submitList(it)
            binding.loadingAnimation.visibility = View.GONE
            binding.llCategoryDetail.visibility = View.VISIBLE}
    }

    private fun goToProductDetailFragment(product: ProductsApiResultItem) {
        val action =
            CategoryDetailFragmentDirections.actionCategoryDetailFragmentToProductDetailFragment(product.id)
        findNavController().navigate(action)
    }
}
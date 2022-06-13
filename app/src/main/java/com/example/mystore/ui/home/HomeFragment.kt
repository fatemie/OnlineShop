package com.example.mystore.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentHomeBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val vModel: HomeViewModel by activityViewModels()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newestAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.newestProductsRecyclerView.adapter = newestAdapter
        vModel.productList.observe(viewLifecycleOwner) { newestAdapter.submitList(it) }

        val mostPopularAdapter = ProductsAdapter {product -> goToProductDetailFragment(product)}
        binding.mostPopularProductsRecyclerView.adapter = mostPopularAdapter
        vModel.mostPopularProduct.observe(viewLifecycleOwner) {mostPopularAdapter.submitList(it)}

        val mostViewAdapter = ProductsAdapter {product -> goToProductDetailFragment(product)}
        binding.mostViewProductsRecyclerView.adapter = mostViewAdapter
        vModel.mostViewProduct.observe(viewLifecycleOwner){ mostViewAdapter.submitList(it)}

    }

    fun goToProductDetailFragment(product : ProductsApiResultItem){
        val action =
            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product.id)
        findNavController().navigate(action)
    }


}
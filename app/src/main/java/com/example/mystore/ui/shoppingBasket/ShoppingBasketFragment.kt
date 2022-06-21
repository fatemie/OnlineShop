package com.example.mystore.ui.shoppingBasket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentShoppingBasketBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.adapter.ShoppingBasketAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingBasketFragment : Fragment() {
    lateinit var binding: FragmentShoppingBasketBinding
    private val vModel: ShoppingBasketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shopping_basket, container, false
        )
        binding.vModel = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ShoppingBasketAdapter ( {product -> goToProductDetailFragment(product)},
            {product -> vModel.onProductChanged(product)} )
        binding.rvShoppingBasket.adapter = adapter
        vModel.shoppingBasketList.observe(viewLifecycleOwner) { adapter.submitList(it) }

        if(vModel.arrayList.isEmpty()){
            binding.emptyMessage.visibility = View.VISIBLE
        }
    }

    private fun goToProductDetailFragment(product: ProductsApiResultItem) {
        val action =
            ShoppingBasketFragmentDirections.actionShoppingBasketFragment2ToProductDetailFragment(
                product.id
            )
        findNavController().navigate(action)
    }

}
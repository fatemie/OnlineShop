package com.example.mystore.ui.shoppingBasket

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentShoppingBasketBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.BaseFragment
import com.example.mystore.ui.adapter.ShoppingBasketAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingBasketFragment : BaseFragment() {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ShoppingBasketAdapter({ product -> goToProductDetailFragment(product) },
            { product -> vModel.onProductChanged(product) })
        binding.rvShoppingBasket.adapter = adapter
        vModel.shoppingBasketList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.loadingAnimation.visibility = View.GONE
            binding.mainLayout.visibility = View.VISIBLE
        }

        if (vModel.basketIsEmpty()) {
            binding.loadingAnimation.visibility = View.GONE
            binding.mainLayout.visibility = View.VISIBLE
            binding.emptyMessage.visibility = View.VISIBLE

        }

        vModel.basketIsEmpty.observe(viewLifecycleOwner){
            if (it){
                binding.emptyMessage.visibility = View.VISIBLE
            }else{
                binding.emptyMessage.visibility = View.GONE
            }
        }

        binding.btnRegisterOrder.setOnClickListener {
            registerOrder()
        }
    }

    private fun goToProductDetailFragment(product: ProductsApiResultItem) {
        val action =
            ShoppingBasketFragmentDirections.actionShoppingBasketFragment2ToProductDetailFragment(
                product.id
            )
        findNavController().navigate(action)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerOrder() {
        if (vModel.isLoggedIn()) vModel.registerBasket(binding.btnRegisterOrder) else {
            showRegisterDialog()
        }
    }


    private fun showRegisterDialog() {
        val builder: AlertDialog.Builder = activity.let {
            AlertDialog.Builder(it)
        }
        builder
            .setTitle("ابتدا باید وارد حساب کاربری شوید")
            .setPositiveButton("باشه", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .show()
    }

}
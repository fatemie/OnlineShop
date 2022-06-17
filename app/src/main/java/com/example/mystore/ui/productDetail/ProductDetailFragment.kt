package com.example.mystore.ui.productDetail
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mystor.R
import com.example.mystor.databinding.FragmentProductDetailBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.home.HomeFragmentDirections
import com.example.mystore.ui.shoppingBasket.ShoppingBasketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private val vModel: ProductDetailViewModel by viewModels()
    private val sharedVModel: ShoppingBasketViewModel by activityViewModels()

    lateinit var binding: FragmentProductDetailBinding

    var productId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt("productId", 0)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_detail, container, false
        )
        binding.vModelDetail = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vModel.getProduct(productId)
        setImage()
        setListener()
    }

    private fun setImage(){
        vModel.product.observe(viewLifecycleOwner){
            Glide.with(this)
                .load(it.images[0].src)
                .fitCenter()
                .into(binding.ivImage)
        }

        vModel.product.observe(viewLifecycleOwner){
            Glide.with(this)
                .load(it.images[1].src)
                .fitCenter()
                .into(binding.ivImage2)
        }

        vModel.product.observe(viewLifecycleOwner){
            Glide.with(this)
                .load(it.images[2].src)
                .fitCenter()
                .into(binding.ivImage2)
        }
    }

    fun setListener(){
        binding.fab.setOnClickListener {
            sharedVModel.addProductToBasket(productId)
            //goToShoppingBasketFragment()
        }
        binding.flipperid.setOnClickListener { binding.flipperid.startFlipping() }
    }

    fun goToShoppingBasketFragment(){
        val action =
            ProductDetailFragmentDirections.actionProductDetailFragmentToShoppingBasketFragment2()
        findNavController().navigate(action)
    }
}
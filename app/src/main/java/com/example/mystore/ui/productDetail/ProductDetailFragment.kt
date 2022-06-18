package com.example.mystore.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mystor.R
import com.example.mystor.databinding.FragmentProductDetailBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.adapter.ImageViewPagerAdapter
import com.example.mystore.ui.adapter.ProductsAdapter
import com.example.mystore.ui.shoppingBasket.ShoppingBasketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private val vModel: ProductDetailViewModel by viewModels()
    private val sharedVModel: ShoppingBasketViewModel by activityViewModels()
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter


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

        vModel.product.observe(viewLifecycleOwner){
            imageViewPagerAdapter = ImageViewPagerAdapter(it.images)
            binding.viewPagerDetail.adapter = imageViewPagerAdapter
        }

        val relatedProductsAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.relatedProductsRecyclerView.adapter = relatedProductsAdapter
        vModel.relatedProducts.observe(viewLifecycleOwner) { relatedProductsAdapter.submitList(it) }

        setUpViewPager()
        setListener()
    }

    private fun setUpViewPager() {


        //set the orientation of the viewpager using ViewPager2.orientation
        binding.viewPagerDetail.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //select any page you want as your starting page
        val currentPageIndex = 1
        binding.viewPagerDetail.currentItem = currentPageIndex

        // registering for page change callback
        binding.viewPagerDetail.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                }
            }
        )
    }

    fun setListener() {
        binding.fab.setOnClickListener {
            sharedVModel.addProductToBasket(productId)
            //goToShoppingBasketFragment()
        }
    }

    fun goToShoppingBasketFragment() {
        val action =
            ProductDetailFragmentDirections.actionProductDetailFragmentToShoppingBasketFragment2()
        findNavController().navigate(action)
    }


    fun goToProductDetailFragment(product: ProductsApiResultItem) {
        val action =
            ProductDetailFragmentDirections.actionProductDetailFragmentSelf(product.id)
        findNavController().navigate(action)
    }


}
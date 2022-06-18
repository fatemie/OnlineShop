package com.example.mystore.ui.home

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mystor.R
import com.example.mystor.databinding.FragmentHomeBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.adapter.ImageViewPagerAdapter
import com.example.mystore.ui.adapter.ProductsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val vModel: HomeViewModel by activityViewModels()
    lateinit var binding: FragmentHomeBinding
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newestAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.newestProductsRecyclerView.adapter = newestAdapter
        vModel.productList.observe(viewLifecycleOwner) {
            newestAdapter.submitList(it)
            imageViewPagerAdapter = ImageViewPagerAdapter(it[10].images)
            binding.viewPager.adapter = imageViewPagerAdapter}

        val mostPopularAdapter = ProductsAdapter {product -> goToProductDetailFragment(product)}
        binding.mostPopularProductsRecyclerView.adapter = mostPopularAdapter
        vModel.mostPopularProduct.observe(viewLifecycleOwner) {mostPopularAdapter.submitList(it)}

        val mostViewAdapter = ProductsAdapter {product -> goToProductDetailFragment(product)}
        binding.mostViewProductsRecyclerView.adapter = mostViewAdapter
        vModel.mostViewProduct.observe(viewLifecycleOwner){ mostViewAdapter.submitList(it)}

        setUpViewPager()
    }

    private fun setUpViewPager() {



        //set the orientation of the viewpager using ViewPager2.orientation
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //select any page you want as your starting page
        val currentPageIndex = 1
        binding.viewPager.currentItem = currentPageIndex

        // registering for page change callback
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        // unregistering the onPageChangedCallback
        binding.viewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun goToProductDetailFragment(product : ProductsApiResultItem){
        if(vModel.isOnline()){
        val action =
            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product.id)
        findNavController().navigate(action)
        }else{
            val snack = Snackbar.make(binding.newestProductsRecyclerView,"خطا در برقراری ارتباط",Snackbar.LENGTH_LONG)
            snack.show()
        }

    }




}
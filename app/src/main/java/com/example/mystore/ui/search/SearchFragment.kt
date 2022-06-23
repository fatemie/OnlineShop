package com.example.mystore.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentSearchBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.ui.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val vModel: SearchViewModel by viewModels()
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search, container, false
        )
        binding.vModel = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()

        val searchAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.rvSearchList.adapter = searchAdapter
        vModel.searchList.observe(viewLifecycleOwner) { searchAdapter.submitList(it) }
    }

    private fun setListener() {
        binding.outlinedTextField.editText?.afterTextChanged {
            if (filterIsChosen()) {
                Log.e("tag", it)
                filterSearch(it)
            } else {
                sortSearch(it)
            }

        }

        binding.btnSort.setOnClickListener {
            if (binding.btnFilter.isVisible) {
                binding.btnFilter.visibility = View.INVISIBLE
                binding.llSort.visibility = View.VISIBLE
            } else {
                binding.btnFilter.visibility = View.VISIBLE
                binding.llSort.visibility = View.GONE
            }
        }
        binding.btnFilter.setOnClickListener {
            if (binding.btnSort.isVisible) {
                binding.btnSort.visibility = View.INVISIBLE
                binding.llFilter.visibility = View.VISIBLE
            } else {
                binding.btnSort.visibility = View.VISIBLE
                binding.llFilter.visibility = View.GONE
            }
        }

    }

    private fun goToProductDetailFragment(product: ProductsApiResultItem) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product.id)
        findNavController().navigate(action)
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    fun filterIsChosen(): Boolean {
        val filter1 = when (binding.filterRadioGroup1.checkedRadioButtonId) {
            binding.blackRadioBtn11.id -> "49"
            binding.greenRadioBtn12.id -> "59"
            else -> "false"
        }
        val filter2 = when (binding.filterRadioGroup2.checkedRadioButtonId) {
            binding.blackRadioBtn11.id -> "49"
            binding.greenRadioBtn12.id -> "59"
            else -> "false"
        }
        return (filter1 != "false" || filter2 != "false")
    }

    fun sortSearch(str: String) {
        val order = when (binding.sortRadioGroup.checkedRadioButtonId) {
            binding.sellRadioBtn.id -> "rating"
            binding.newestRadioBtn.id -> "date"
            binding.expensiveRadioBtn.id -> "price"
            binding.cheapRadioBtn.id -> "price"
            else -> "date"
        }
        vModel.getSearchedProducts(searchStr = str, order = order)
    }

    fun filterSearch(str: String) {
        val filter1 = when (binding.filterRadioGroup1. checkedRadioButtonId) {
            binding.mRadioBtn11.id -> "m"
            binding.lRadioBtn12.id -> "l"
            binding.xlRadioBtn12.id -> "xl"
            else -> "false"
        }
        val filter2 = when (binding.filterRadioGroup2.checkedRadioButtonId) {
            binding.blackRadioBtn11.id -> "49"
            binding.greenRadioBtn12.id -> "59"
            else -> "false"
        }
        when (filter2) {
            "small" -> vModel.searchWithFilter("pa_size", "small", str)
            "big" -> vModel.searchWithFilter("pa_size", "big", str)
            "49" -> vModel.searchWithFilter("pa_color", "49", str)
            "59" -> vModel.searchWithFilter("pa_color", "59", str)
        }
    }


}
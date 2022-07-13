package com.example.mystore.ui.search

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentSearchBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.attributeTerm.AttributeTermItem
import com.example.mystore.domain.isOnline
import com.example.mystore.ui.BaseFragment
import com.example.mystore.ui.adapter.AttributeItemAdapter
import com.example.mystore.ui.adapter.ProductsAdapter
import com.example.mystore.ui.productDetail.ProductDetailFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private val vModel: SearchViewModel by viewModels()
    lateinit var binding: FragmentSearchBinding
    lateinit var colorTermsAdapter: AttributeItemAdapter
    lateinit var sizeTermsAdapter: AttributeItemAdapter

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()

        val searchAdapter = ProductsAdapter { product -> goToProductDetailFragment(product) }
        binding.rvSearchList.adapter = searchAdapter
        vModel.searchList.observe(viewLifecycleOwner) { searchAdapter.submitList(it) }

        colorTermsAdapter = AttributeItemAdapter { attributeTerm -> getColorAttributeId(attributeTerm) }
        binding.rvColorTerm.adapter = colorTermsAdapter
        vModel.colorTerms.observe(viewLifecycleOwner) { colorTermsAdapter.submitList(it) }

        sizeTermsAdapter = AttributeItemAdapter { attributeTerm -> getSizeAttributeId(attributeTerm) }
        binding.rvSizeTerm.adapter = sizeTermsAdapter
        vModel.sizeTerms.observe(viewLifecycleOwner) { sizeTermsAdapter.submitList(it) }
    }

    private fun setListener() {
        binding.outlinedTextField.editText?.afterTextChanged {
            val order = when (binding.sortRadioGroup.checkedRadioButtonId) {
                binding.sellRadioBtn.id -> "rating"
                binding.newestRadioBtn.id -> "date"
                binding.expensiveRadioBtn.id -> "price"
                binding.cheapRadioBtn.id -> "price"
                else -> "date"
            }
            if (filterIsChoosed()) {
                filterSearch(it, order)
            } else {
                sortSearch(it, order)
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

    fun getColorAttributeId(attribute_term: AttributeTermItem){
        vModel.colorTermId = attribute_term.id.toString()
        vModel.sizeTermId = ""
        for (item in vModel.colorTerms.value!!){
            if(item.id != attribute_term.id)
                item.isChoosed = false
        }
        colorTermsAdapter.submitList(vModel.colorTerms.value)
    }

    fun getSizeAttributeId(attribute_term: AttributeTermItem){
        vModel.sizeTermId = attribute_term.id.toString()
        vModel.colorTermId = ""
        sizeTermsAdapter.submitList(vModel.sizeTerms.value)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun goToProductDetailFragment(product: ProductsApiResultItem) {
        if(isOnline(requireActivity().application)){
            val action =
                SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product.id)
            findNavController().navigate(action)
        }else{
            val snack = Snackbar.make(binding.rvSearchList,"خطا در برقراری ارتباط",
                Snackbar.LENGTH_LONG)
            snack.show()
        }
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

    private fun filterIsChoosed(): Boolean {
        return (binding.llFilter.isVisible)
    }

    fun sortSearch(str: String, order : String) {
        vModel.getSearchedProducts(searchStr = str, order = order)
    }

    fun filterSearch(str: String, orderBy : String) {
        if(vModel.colorTermId != "")
            vModel.searchWithFilter("pa_color", vModel.colorTermId, str, orderBy)
        if(vModel.sizeTermId != "")
            vModel.searchWithFilter("pa_size", vModel.sizeTermId, str, orderBy)

    }


}
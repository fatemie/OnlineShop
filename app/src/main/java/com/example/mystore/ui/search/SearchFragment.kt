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
import com.example.mystore.ui.adapter.AttributeItemAdapter
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

        val colorTermsAdapter = AttributeItemAdapter { attributeTerm -> vModel.getColorAttributeId(attributeTerm) }
        binding.rvColorTerm.adapter = colorTermsAdapter
        vModel.colorTerms.observe(viewLifecycleOwner) { colorTermsAdapter.submitList(it) }

        val sizeTermsAdapter = AttributeItemAdapter { attributeTerm -> vModel.getSizeAttributeId(attributeTerm) }
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
                Log.e("tag", it)
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

    private fun filterIsChoosed(): Boolean {
        return (binding.btnFilter.isVisible)
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
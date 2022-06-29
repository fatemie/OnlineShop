package com.example.mystore.ui.login

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystor.R
import com.example.mystor.databinding.FragmentLoginBinding
import com.example.mystore.data.model.ProductsApiResultItem
import com.example.mystore.data.model.customer.Billing
import com.example.mystore.data.model.customer.CustomerItem
import com.example.mystore.ui.BaseFragment
import com.example.mystore.ui.search.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalDateTime.now
import java.util.*

@AndroidEntryPoint
class LoginFragment : BaseFragment(){
    lateinit var binding: FragmentLoginBinding
    private val vModel: LoginViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )
        binding.vModel = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            if(verification()) {
                val customer = CustomerItem(
                    "https://secure.gravatar.com/avatar/8eb1b522f60d11fa897de1dc6351b7e8?s=96",
                    Billing("","","Tehran","","Iran","",
                        binding.TextFieldFirstName.editText!!.text.toString(),
                        binding.TextFieldLastName.editText!!.text.toString(),
                        binding.TextFieldPhone.editText!!.text.toString(),"",""),
                        LocalDate.now().toString(),
                    "",
                    binding.TextFieldFirstName.editText!!.text.toString(), 1,false,
                    binding.TextFieldLastName.editText!!.text.toString(),
                    "customer",
                    binding.TextFieldPass.editText!!.text.toString()
                )

                vModel.saveInfoLoginToSharedPref(customer)
                goToProfileFragment()
            }
        }
        if (vModel.isLogin()){
            goToProfileFragment()
        }
    }

    private fun goToProfileFragment() {
        val action =
            LoginFragmentDirections.actionLoginFragment2ToProfileFragment()
        findNavController().navigate(action)
    }

    fun verification(): Boolean {
        var allFieldComplete = false
        val editTextArray = arrayListOf(
            binding.TextFieldFirstName,
            binding.TextFieldLastName,
            binding.TextFieldPhone,
            binding.TextFieldPass,
            binding.TextFieldRePass
        )
        for (editText in editTextArray) {
            if (editText != null) {
                vModel.checkFieldComplete(editText)
            }
        }
        for (editText in editTextArray) {
            if (editText != null) {
                if (!vModel.checkFieldComplete(editText)) {
                    return false
                }
            }
        }
        for (editText in editTextArray) {
            if (editText != null) {
                editText.error = null
            }
        }

        if (vModel.verifyPass(binding.TextFieldPass)) {
            binding.TextFieldPass.error = null
            if (binding.TextFieldPass.editText!!.text.toString() == binding.TextFieldRePass.editText!!.text.toString()) {
                binding.TextFieldRePass.error = null
                return true
            } else {
                binding.TextFieldRePass.error = "تکرار کلمه عبور همخوانی ندارد"
                allFieldComplete = false
            }
        } else {
            allFieldComplete = false
        }
        return allFieldComplete
    }

}
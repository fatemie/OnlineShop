package com.example.mystore.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mystor.R
import com.example.mystor.databinding.FragmentProfileBinding
import com.example.mystore.data.model.customer.Billing
import com.example.mystore.data.model.customer.CustomerItem
import com.example.mystore.ui.BaseFragment
import com.example.mystore.ui.errorException
import com.example.mystore.ui.shoppingBasket.ShoppingBasketViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var binding: FragmentProfileBinding
    private val vModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile, container, false
        )
        binding.vModel = vModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(vModel.avatar)
            .fitCenter()
            .circleCrop()
            .into(binding.ivProfileImage)

        setListener()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setListener() {
        binding.btnDeleteAccount.setOnClickListener {
            showDeleteDialog()
        }
        binding.btnPersonalInfo.setOnClickListener {
            binding.InfoLayout.visibility = View.VISIBLE
            binding.firstLayout.visibility = View.GONE
        }
        binding.btnRegister.setOnClickListener {
            if (verification()) {
                val customer = CustomerItem(
                    "https://secure.gravatar.com/avatar/8eb1b522f60d11fa897de1dc6351b7e8?s=96",
                    Billing(
                        binding.TextFieldAddress.editText!!.text.toString(),
                        "", "Tehran", "Iran", "",
                        binding.TextFieldEmail.editText!!.text.toString(),
                        binding.TextFieldFirstName1.editText!!.text.toString(),
                        binding.TextFieldLastName1.editText!!.text.toString(),
                        binding.TextFieldPhone.editText!!.text.toString(),
                        binding.TextFieldPostalCode.editText!!.text.toString(), ""
                    ),
                    LocalDate.now().toString(),
                    binding.TextFieldEmail.editText!!.text.toString(),
                    binding.TextFieldFirstName1.editText!!.text.toString(), 1, false,
                    binding.TextFieldLastName1.editText!!.text.toString(),
                    "customer",
                    binding.TextFieldPass.editText!!.text.toString()
                )
                vModel.saveInfoProfileToSharedPref(customer)
                vModel.registerNewCustomerInServer(binding.btnRegister)

                binding.InfoLayout.visibility = View.GONE
                binding.firstLayout.visibility = View.VISIBLE
            }
        }
    }

    fun goToLoginFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment2()
        findNavController().navigate(action)
    }

    fun verification(): Boolean {
        val editTextArray = arrayListOf(
            binding.TextFieldFirstName1,
            binding.TextFieldLastName1,
            binding.TextFieldEmail,
            binding.TextFieldAddress,
            binding.TextFieldPostalCode
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
        return vModel.verifyPostalCode(binding.TextFieldPostalCode)
    }

    private fun showDeleteDialog() {
        val builder: AlertDialog.Builder = activity.let {
            AlertDialog.Builder(it)
        }
        builder
            .setTitle("خروج از حساب کاربری")
            .setPositiveButton("بله", DialogInterface.OnClickListener {
                    dialog, id -> vModel.deleteAccount()
                                    goToLoginFragment()
            })
            .setNegativeButton("خیر", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
            .show()
    }

}
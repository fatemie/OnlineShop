package com.example.mystore.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mystor.R
import com.example.mystor.databinding.FragmentProfileBinding
import com.example.mystore.data.model.customer.Billing
import com.example.mystore.data.model.customer.Customer
import com.example.mystore.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var binding: FragmentProfileBinding
    private val vModel: LoginViewModel by activityViewModels()
    //lateinit var prefs : SharedPreferences

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

        prefs = requireActivity().getSharedPreferences(resources.getString(R.string.app_name),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor =  prefs.edit()

        binding.btnChangeTheme.setOnClickListener {
            if(binding.radioGroupTheme.isVisible){
                binding.radioGroupTheme.visibility = View.GONE
            }else{
                binding.radioGroupTheme.visibility = View.VISIBLE
            }
            val theme = when(binding.radioGroupTheme.checkedRadioButtonId){
                binding.redBtn.id -> 1
                binding.blueBtn.id -> 2
                else -> 0
            }
            editor.putInt("THEME", theme)
            if(theme == 1){
                activity?.setTheme(R.style.Theme_MyStor)
            }else{
                activity?.setTheme(R.style.Theme_MyStor1)
            }
        }


        binding.btnDeleteAccount.setOnClickListener {
            showDeleteDialog()
        }
        binding.btnPersonalInfo.setOnClickListener {
            binding.llShowInfo.visibility = View.VISIBLE
            binding.firstLayout.visibility = View.GONE
        }
        binding.btnEdit.setOnClickListener {
            binding.llShowInfo.visibility = View.GONE
            binding.editInfoLayout.visibility = View.VISIBLE
        }
        binding.btnRegister.setOnClickListener {
            if (verification()) {
                val customer = Customer(
                    binding.TextFieldEmail.editText!!.text.toString(),
                    binding.TextFieldFirstName1.editText!!.text.toString(),
                    binding.TextFieldLastName1.editText!!.text.toString(),
                    Billing(
                        binding.TextFieldAddress.editText!!.text.toString(),
                        "", "Tehran", "Iran", "",
                        binding.TextFieldEmail.editText!!.text.toString(),
                        binding.TextFieldFirstName1.editText!!.text.toString(),
                        binding.TextFieldLastName1.editText!!.text.toString(),
                        binding.TextFieldPhone.editText!!.text.toString(),
                        binding.TextFieldPostalCode.editText!!.text.toString(), ""
                    ),
                    binding.TextFieldPass.editText!!.text.toString(),
                )
                vModel.saveInfoProfileToSharedPref(customer)
                vModel.registerNewCustomerInServer(binding.btnRegister)

                binding.editInfoLayout.visibility = View.GONE
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
            .setPositiveButton("بله", DialogInterface.OnClickListener { dialog, id ->
                vModel.deleteAccount()
                goToLoginFragment()
            })
            .setNegativeButton("خیر", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .show()
    }

}
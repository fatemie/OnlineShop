package com.example.mystore.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mystor.R
import com.example.mystor.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(vModel.avatar)
            .fitCenter()
            .circleCrop()
            .into(binding.ivProfileImage)

        setListener()

    }

    fun setListener() {
        binding.btnDeleteAccount.setOnClickListener {
            vModel.deleteAccount()
            goToLoginFragment()
        }
        binding.btnPersonalInfo.setOnClickListener {
            binding.InfoLayout.visibility = View.VISIBLE
            binding.firstLayout.visibility = View.GONE
        }
        binding.btnRegister.setOnClickListener {
            if(verification()){
                binding.InfoLayout.visibility = View.GONE
                binding.firstLayout.visibility = View.VISIBLE
            }
        }
    }

    fun goToLoginFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment2()
        findNavController().navigate(action)
    }

    fun verification() :Boolean{
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

}
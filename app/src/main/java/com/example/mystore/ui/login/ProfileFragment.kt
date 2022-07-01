package com.example.mystore.ui.login

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent.getService
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName
import androidx.core.location.LocationManagerCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var binding: FragmentProfileBinding
    private val vModel: LoginViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 2
    var address2 = ""

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

        prefs = requireActivity().getSharedPreferences(
            resources.getString(R.string.app_name),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()

        binding.btnLocation.setOnClickListener {
            activity?.let { it1 ->
                ActivityCompat.requestPermissions(
                    it1,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermissionCode
                )
            }

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            getLocation()

        }

        binding.btnChangeTheme.setOnClickListener {
            if (binding.radioGroupTheme.isVisible) {
                binding.radioGroupTheme.visibility = View.GONE
            } else {
                binding.radioGroupTheme.visibility = View.VISIBLE
            }
            val theme = when (binding.radioGroupTheme.checkedRadioButtonId) {
                binding.redBtn.id -> 1
                binding.blueBtn.id -> 2
                else -> 0
            }
            editor.putInt("THEME", theme)
            if (theme == 1) {
                activity?.setTheme(R.style.Theme_MyStor)
            } else {
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
                        address2, "Tehran", "Iran", "",
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


    @SuppressLint("LongLogTag")
    fun getLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    Toast.makeText(
                        activity,
                        "latitude= " + it.latitude + " , long= " + it.longitude,
                        Toast.LENGTH_LONG
                    ).show()
                    address2 = "latitude= " + it.latitude + " , long= " + it.longitude
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
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
package com.example.mystore.ui.login

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystor.R
import com.example.mystore.data.ProductRepository
import com.example.mystore.data.model.customer.Customer
import com.example.mystore.data.model.customer.CustomerItem
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val FIRSTNAME = "FIRSTNAME"
const val LASTNAME = "LASTNAME"
const val EMAIL = "EMAIL"
const val PASS = "PASS"
const val PHONE = "PHONE"
const val ADDRESS = "ADDRESS"
const val POSTALCODE = "POSTALCODE"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val app: Application
) : AndroidViewModel(app) {

    lateinit var prefs: SharedPreferences

    lateinit var customer: CustomerItem
    var firstName = ""
    var lastName = ""
    var avatar = ""
    var pass = ""

    init {
        isLogin()
    }

    fun verifyPass(editText: TextInputLayout): Boolean {
        return if (editText.editText!!.text.length > 3) {
            true
        } else {
            editText.error = "کلمه عبور باید حداقل شامل چهار کارکتر باشد"
            false
        }
    }

    fun verifyPostalCode(editText: TextInputLayout): Boolean {
        return if (editText.editText!!.text.length == 10) {
            true
        } else {
            editText.error = " کد پستی باید شامل ده رقم باشد"
            false
        }
    }

    fun checkFieldComplete(editText: TextInputLayout): Boolean {
        if (editText.editText!!.text.isNullOrEmpty()) {
            editText.error = "لطفا این فیلد را تکمیل کنید"
            return false
        }
        editText.error = null
        return true
    }

    fun saveInfoLoginToSharedPref(thisCustomer: CustomerItem) {
        prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString(FIRSTNAME, thisCustomer.firstName)
        editor.putString(LASTNAME, thisCustomer.lastName)
        editor.putString(PHONE, thisCustomer.billing.phone)
//        editor.putString(PASS , thisCustomer.username)
        editor.apply()
    }

    fun saveInfoProfileToSharedPref(thisCustomer: CustomerItem) {
        prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString(FIRSTNAME, thisCustomer.firstName)
        editor.putString(LASTNAME, thisCustomer.lastName)
        editor.putString(EMAIL, thisCustomer.email)
        editor.putString(ADDRESS, thisCustomer.billing.address1)
        editor.putString(POSTALCODE, thisCustomer.billing.postcode)
        editor.apply()
        customer = thisCustomer
    }

    fun isLogin(): Boolean {

        val prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        firstName = prefs.getString(FIRSTNAME, "").toString()
        lastName = prefs.getString(LASTNAME, "").toString()
        pass = prefs.getString(PASS, "").toString()
        avatar = "https://secure.gravatar.com/avatar/be7b5febff88a2d947c3289e90cdf017?s=96"

        return (!firstName.isNullOrBlank())
    }

    fun deleteAccount() {
        prefs = app.getSharedPreferences(
            R.string.app_name.toString(),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString(FIRSTNAME, "")
        editor.putString(LASTNAME, "")
        editor.putString(EMAIL, "")
        editor.putString(PASS, "")
        editor.apply()
    }

    fun registerNewCustomerInServer() {
        try {
            viewModelScope.launch {
                val customerForServer = Customer(customer.id,
                    customer.email, customer.firstName, customer.lastName,
                    customer.billing
                )
                repository.register(customerForServer)
            }
        } catch (e: Exception) {
            Toast.makeText(app, "اطلاعات در سرور ذخیره نشد", Toast.LENGTH_SHORT).show()
        }
    }


}
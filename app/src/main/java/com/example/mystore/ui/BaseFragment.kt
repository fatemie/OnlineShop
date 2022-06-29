package com.example.mystore.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.mystor.R
import dagger.hilt.android.AndroidEntryPoint
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

var errorException = MutableLiveData<Exception?>()
var customerRegisterOK = true
var orderRegisterOK = true
var reviewRegisterOK = true

@AndroidEntryPoint
open class BaseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorException.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is UnknownHostException -> showDialog("به اینترنت متصل نیستید!")
                    is SocketTimeoutException -> showDialog("به اینترنت متصل نیستید!")
                    is retrofit2.HttpException -> showDialog("خطا در درخواست ارسال")
                    is ConnectException -> showDialog("خطای سرور")
                    is SocketException -> showDialog("خطای سرور")
                    else -> it.message?.let{m -> showDialog(m)}
                }
            }
        }
    }

    fun showDialog(message: String) {
        val builder: AlertDialog.Builder = activity.let {
            AlertDialog.Builder(it)
        }
        builder.setMessage(message)
            .setTitle("خطا")
            .show()
        errorException.value = null
    }

}
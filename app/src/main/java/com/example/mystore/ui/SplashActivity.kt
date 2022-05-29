package com.example.mystore.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mystor.R
import com.example.mystor.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        binding.tvAppName.alpha = 0f
        binding.tvAppName.animate().setDuration(3000).alpha(1f).withEndAction {
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
        binding.iconSplash.alpha= 0f
        binding.iconSplash.animate().setDuration(3000).alpha(1f).withEndAction {
            binding.fragmentContainerView.visibility = View.VISIBLE
            binding.layout.visibility = View.GONE
            supportActionBar?.show()
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }
}
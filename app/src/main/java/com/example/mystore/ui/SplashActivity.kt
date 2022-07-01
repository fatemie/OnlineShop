package com.example.mystore.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mystor.R
import com.example.mystor.databinding.ActivityMainBinding
import com.example.mystore.domain.isOnline
import com.example.mystore.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator3

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val vModel: HomeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        binding.tvAppName.alpha = 0f
        binding.tvAppName.animate().setDuration(2000).alpha(1f).withEndAction {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        binding.iconSplash.alpha = 0f
        binding.iconSplash.animate().setDuration(2000).alpha(1f).withEndAction {
            checkConnection()

            binding.btnConnection.setOnClickListener {
             checkConnection()
            }

        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        setupWithNavController(binding.bottomNav, navHostFragment!!.navController)




    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkConnection(){
        if (isOnline(application)) {
            binding.homeLayout.visibility = View.VISIBLE
            binding.layout.visibility = View.GONE
            supportActionBar?.show()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            vModel.getProducts()
        } else {
            binding.tvConnection.visibility = View.VISIBLE
            binding.btnConnection.visibility = View.VISIBLE
        }
    }
}
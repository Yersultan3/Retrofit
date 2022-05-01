package com.example.retrofit.view.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.retrofit.R
import com.example.retrofit.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navController = findNavController(R.id.nav_host_fragment)
        initBottomNav()


        val sharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)
        if (token == null) {
            Log.d("Token", " Token Null")
        }
    }

    private fun initBottomNav() {
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.setupWithNavController(navController)
    }


}
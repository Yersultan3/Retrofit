package com.example.retrofit.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.retrofit.R
import com.example.retrofit.databinding.ActivityUnauthorizedBinding

class UnauthorizedActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUnauthorizedBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnauthorizedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = findNavController(R.id.nav_authorization_fragment)
    }
}
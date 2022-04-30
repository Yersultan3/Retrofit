package com.example.retrofit.view.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.retrofit.R
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.view.authorization.ProfileFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
//    val args: MainActivityArgs by navArgs()

//    val currentUser = args.user.userName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        var tab_toolbar = findViewById<Toolbar>(R.id.toolbar)
        navController = findNavController(R.id.nav_host_fragment)
        initBottomNav()

        val bundle = Bundle()
//        bundle.putString("params", args.user.userName)
//        val fragment = ProfileFragment()
//        fragment.arguments = bundle
//        Log.d("CurrentUser", bundle.toString())
        val sharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)
        if (token == null) {
            // intent to login page
            Log.d("Token", " Token Null")
//            navController.navigate(MainActivityDirections.actionMainActivityToLoginFragment())
        }


    }

    private fun initBottomNav() {
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.setupWithNavController(navController)
//        args.user.userName
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
}
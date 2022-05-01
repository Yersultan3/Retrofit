package com.example.retrofit.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.retrofit.databinding.FragmentRegistrationBinding

class RegistrationFragment: Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun displayUsersList() {
        Log.i("MYTAG","insidisplayUsersList")
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }



}
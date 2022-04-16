package com.example.retrofit.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.retrofit.R
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginFragment: Fragment(), CoroutineScope {

    private lateinit var binding: FragmentLoginBinding
    private val job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToReg.setOnClickListener {
            if(binding.emailTv.text.isNullOrBlank() && binding.passwordTv.text.isNullOrBlank()) {

                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
            else {
                Toast.makeText(context, "Email or Password is empty", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun createToken() {
        launch {
            val response = RetrofitService.getPostApi().createToken("02c64fae28c1003e5a0725abd7c2e518")
            val body = response.body()
            if (response.isSuccessful) {

            } else {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
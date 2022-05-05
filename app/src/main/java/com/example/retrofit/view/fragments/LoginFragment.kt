package com.example.retrofit.view.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.retrofit.databinding.FragmentLoginBinding
import com.example.retrofit.model.api.LoginApprove
import com.example.retrofit.view.activities.UnauthorizedActivity
import com.example.retrofit.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class LoginFragment: Fragment(), CoroutineScope {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings =
            context?.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE) as SharedPreferences
        editor = prefSettings.edit()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreferences = context?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("TOKEN", null)
        if (token != null) {
            Log.d("Token", " Token Null")
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToMainActivity()
            )
        }
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        onLoginClick()
    }


    companion object {
        private var sessionId: String = ""
        const val APP_SETTINGS = "Settings"
        const val SESSION_ID_KEY = "SESSION_ID"
    }
    private fun onLoginClick() {
        binding.submitButton.setOnClickListener {
            hideKeyboard(requireActivity())
            if (!binding.userNameTextField.text.isNullOrBlank() && !binding.passwordTextField.text.isNullOrBlank()) {
                val data = LoginApprove(
                    username = binding.userNameTextField.text.toString().trim(),
                    password = binding.passwordTextField.text.toString().trim(),
                    request_token = ""
                )
                loginViewModel.login(data)
                observeLoadingState()
            } else {
                Toast.makeText(requireContext(), "Введите данные", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    private fun initViewModel() {
        loginViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            )[LoginViewModel::class.java]
    }
    private fun observeLoadingState() {

        loginViewModel.sessionId.observe(viewLifecycleOwner) {
            sessionId = it
            putDataIntoPref(sessionId)
            try {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToMainActivity()
                )
            } catch (e: Exception) {

              }
        }
    }

    private fun putDataIntoPref(string: String) {
        editor.putString(SESSION_ID_KEY, string)
        editor.commit()
        binding.userNameTextField.text = null
        binding.passwordTextField.text = null
    }
}
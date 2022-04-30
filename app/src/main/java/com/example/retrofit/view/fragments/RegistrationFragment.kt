package com.example.retrofit.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.retrofit.databinding.FragmentRegistrationBinding
import com.example.retrofit.viewModel.RegisterViewModel

class RegistrationFragment: Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnToLogin.setOnClickListener{
//            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
//        }

//        val application = requireNotNull(this.activity).application
//
//        val dao = UserDatabase.getInstance(application).registerDatabaseDao
//
//        val repository = RegisterRepo(dao)
//
//        val factory = RegisterViewModelFactory(repository, application)
//
//        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
//
//        binding.myViewModel = registerViewModel
//
//        binding.lifecycleOwner = this
//
//        registerViewModel.navigateto.observe(this, Observer { hasFinished->
//            if (hasFinished == true){
//                Log.i("MYTAG","insidi observe")
//                displayUsersList()
//                registerViewModel.doneNavigating()
//            }
//        })
//
//        registerViewModel.userDetailsLiveData.observe(this, Observer {
//            Log.i("MYTAG",it.toString()+"000000000000000000000000")
//        })
//
//
//        registerViewModel.errotoast.observe(this, Observer { hasError->
//            if(hasError==true){
//                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
//                registerViewModel.donetoast()
//            }
//        })
//
//        registerViewModel.errotoastUsername.observe(this, Observer { hasError->
//            if(hasError==true){
//                Toast.makeText(requireContext(), "UserName Already taken", Toast.LENGTH_SHORT).show()
//                registerViewModel.donetoastUserName()
//            }
//        })
    }

    private fun displayUsersList() {
        Log.i("MYTAG","insidisplayUsersList")
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }



}
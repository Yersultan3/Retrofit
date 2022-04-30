package com.example.retrofit.view.authorization

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.retrofit.databinding.FragmentProfileBinding
import com.example.retrofit.model.api.LoginApprove
import com.example.retrofit.view.fragments.LoginFragmentDirections
import com.example.retrofit.viewModel.LoginViewModel


class ProfileFragment: Fragment()  {


    private lateinit var binding: FragmentProfileBinding

    private lateinit var profileViewModel: LoginViewModel
//    private val args: ProfileFragmentArgs by navArgs()
//    private var currentUser = MainActivity().args.user.userName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application

//        val dao = UserDatabase.getInstance(application).registerDatabaseDao

//        val repository = RegisterRepo(dao)

//        val factory = ProfileViewModelFactory(repository, application)

//        profileViewModel =
//            ViewModelProvider(this, factory)[ProfileViewModel::class.java]
//
//        binding.profileLayout = profileViewModel

        binding.lifecycleOwner = this

        val bundle = arguments
        val message = bundle?.getString("params")
        Log.d("CurrentUser2", message.toString())


//        profileViewModel.navigateto.observe(this, Observer { hasFinished ->
//            if (hasFinished == true) {
//                val action = UserDetailsFragmentDirections.actionUserDetailsFragmentToLoginFragment()
//                NavHostFragment.findNavController(this).navigate(action)
//                profileViewModel.doneNavigating()
//            }
//        })

        initRecyclerView()
        initViewModel()
        showDialog()
    }

    private fun initViewModel() {
        profileViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            )[LoginViewModel::class.java]
    }

    private fun showDialog() {
        binding.btnLogout.setOnClickListener{
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Delete Wallpaper")
            dialog.setMessage("Do you really want to logout?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->



                val sharedPreferences = requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                sharedPreferences.edit().remove("TOKEN").apply()
                val token = sharedPreferences.getString("TOKEN", null)

                if(token == null) {
                    Toast.makeText(context,
                        "token null", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                }

            })
            dialog.setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, which ->
                dialog.dismiss()
            })
            dialog.show()
        }
    }

    private fun initRecyclerView() {
//        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
//        displayUsersList()
    }


}




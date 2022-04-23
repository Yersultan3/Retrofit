package com.example.retrofit.view.authorization

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.retrofit.databinding.FragmentProfileBinding

class ProfileFragment: Fragment()  {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDialog()
    }

    private fun showDialog() {
        binding.btnLogout.setOnClickListener{
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Delete Wallpaper")
            dialog.setMessage("Do you really want to logout?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context,
                    "Hello", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            })
            dialog.setNegativeButton("No", DialogInterface.OnClickListener{
                    dialog, which ->
                dialog.dismiss()
            })

            dialog.show()
        }
    }


}




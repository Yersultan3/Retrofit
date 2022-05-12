package com.example.retrofit.view.authorization

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.retrofit.databinding.FragmentProfileBinding
import com.example.retrofit.viewModel.LoginViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener


class ProfileFragment: Fragment()  {

    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        initRecyclerView()
        initViewModel()
        showDialog()



        binding.fabAddPhoto.setOnClickListener {
            checkStoragePermission()
        }

    }

    private fun checkStoragePermission() {

        Dexter.withContext(context).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    context,
                    "You have denied the storage permission to select image",
                    Toast.LENGTH_SHORT
                ).show()
                showRotationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?, p1: PermissionToken?) {
                showRotationalDialogForPermission()
            }
        }).onSameThread().check()
    }

    private fun cameraCheckPermission() {

        Dexter.withContext(context)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }


    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    val bitmap = data?.extras?.get("data") as Bitmap

                    //we are using coroutine image loader (coil)
                    binding.imgProfile.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                        size(150, 150)
                        transformations(CircleCropTransformation())
                    }
                }

                GALLERY_REQUEST_CODE -> {

                    binding.imgProfile.load(data?.data) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }

                }
            }

        }

    }


    private fun showRotationalDialogForPermission() {
        androidx.appcompat.app.AlertDialog.Builder(requireActivity())
            .setMessage("It looks like you have turned off permissions"
                    + "required for this feature. It can be enable under App settings!!!")

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context?.packageName, null)

                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }



    private fun initViewModel() {
        profileViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            )[LoginViewModel::class.java]
    }



    private fun showDialog() {
        binding.btnLogout.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Log out")
            dialog.setMessage("Do you really want to logout?")
            dialog.setPositiveButton("Yes") { dialog, which ->

                val sharedPreferences =
                    requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                sharedPreferences.edit().remove("TOKEN").apply()
                val token = sharedPreferences.getString("TOKEN", null)

                if (token == null) {
                    Toast.makeText(
                        context,
                        "token null", Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUnauthorizedActivity())
                }
            }

            dialog.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun initRecyclerView() {

    }


}




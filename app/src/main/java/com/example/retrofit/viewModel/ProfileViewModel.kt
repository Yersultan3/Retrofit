//package com.example.retrofit.viewModel
//
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//
//class ProfileViewModel (private val repository: RegisterRepo, application: Application):AndroidViewModel(application) {
//
//    val users = repository.users
//    init {
//        Log.i("MYTAG","inside_users_Lisrt_init")
//    }
//
//
//    private val _navigateto = MutableLiveData<Boolean>()
//
//    val navigateto: LiveData<Boolean>
//        get() = _navigateto
//
//    fun doneNavigating(){
//        _navigateto.value=false
//    }
//
//    fun backButtonclicked(){
//        _navigateto.value = true
//    }
//
//}
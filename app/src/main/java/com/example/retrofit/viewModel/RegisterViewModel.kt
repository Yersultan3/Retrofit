package com.example.retrofit.viewModel

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.retrofit.model.database.RegisterRepo
import com.example.retrofit.model.database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class RegisterViewModel(private val repository: RegisterRepo, application: Application):
   AndroidViewModel(application), Observable {

   private var userdata: String? = null

   var userDetailsLiveData = MutableLiveData<Array<User>>()

   @Bindable
   val inputUsername = MutableLiveData<String>()

   @Bindable
   val inputPassword = MutableLiveData<String>()

   private val viewModelJob = Job()
   private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


   private val _navigateto = MutableLiveData<Boolean>()

   val navigateto: LiveData<Boolean>
      get() = _navigateto

   private val _errorToast = MutableLiveData<Boolean>()

   val errotoast: LiveData<Boolean>
      get() = _errorToast

   private val _errorToastUsername = MutableLiveData<Boolean>()

   val errotoastUsername: LiveData<Boolean>
      get() = _errorToastUsername


   fun submitButton() {
      Log.i("MYTAG", "Inside SUBMIT BUTTON")
      if (
//         inputFirstName.value == null || inputLastName.value == null ||
         inputUsername.value == null || inputPassword.value == null) {
         _errorToast.value = true
      } else {
         uiScope.launch {
//            withContext(Dispatchers.IO) {
            val usersNames = repository.getUserName(inputUsername.value!!)
            Log.i("MYTAG", usersNames.toString() + "------------------")
            if (usersNames != null) {
               _errorToastUsername.value = true
               Log.i("MYTAG", "Inside if Not null")
            } else {
               Log.i("MYTAG", userDetailsLiveData.value.toString() + "ASDFASDFASDFASDF")
               Log.i("MYTAG", "OK im in")
//               val firstName = inputFirstName.value!!
//               val lastName = inputLastName.value!!
               val email = inputUsername.value!!
               val password = inputPassword.value!!
               Log.i("MYTAG", "insidi Sumbit")
               insert(User(0, email, password))
//               inputFirstName.value = null
//               inputLastName.value = null
               inputUsername.value = null
               inputPassword.value = null
               _navigateto.value = true
            }
         }
      }
   }


   override fun onCleared() {
      super.onCleared()
   }

   fun doneNavigating() {
      _navigateto.value = false
      Log.i("MYTAG", "Done navigating ")
   }

   fun donetoast() {
      _errorToast.value = false
      Log.i("MYTAG", "Done taoasting ")
   }

   fun donetoastUserName() {
      _errorToast.value = false
      Log.i("MYTAG", "Done taoasting  username")
   }

   private fun insert(user: User): Job = viewModelScope.launch {
      repository.insert(user)
   }

//    fun clearALl():Job = viewModelScope.launch {
//        repository.deleteAll()
//    }

   override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

   }

   override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

   }
}
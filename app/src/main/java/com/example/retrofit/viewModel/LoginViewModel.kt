package com.example.retrofit.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.model.api.LoginApprove
import com.example.retrofit.model.api.Session
import com.example.retrofit.repository.MovieRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application
    private val repository = MovieRepository(application)
    private val apiService = RetrofitService.getInstance()

    private val _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String>
        get() = _sessionId

    fun login(data: LoginApprove) {
        viewModelScope.launch {
            val sessionId =repository.login(data)
            if (sessionId.isNotBlank()) {
                _sessionId.value = sessionId
            }
//            val responseGet = apiService.getToken()
//            if (responseGet.isSuccessful) {
//
//                val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//                sharedPreference.edit().putString("TOKEN", responseGet.body()?.request_token).apply()
//
//                val loginApprove = LoginApprove(
//                    username = data.username,
//                    password = data.password,
//                    request_token = responseGet.body()?.request_token as String
//                )
//                val responseApprove = apiService.approveToken(loginApprove = loginApprove)
//                if (responseApprove.isSuccessful) {
//                    val session =
//                        apiService.createSession(token = responseApprove.body() as Token)
//                    if (session.isSuccessful) {
//                        _sessionId.value = session.body()?.session_id
//                    }
//                } else {
//                    Toast.makeText(context, "Неверные данные", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
    }

    fun deleteSession(session: String) {
        viewModelScope.launch {
            apiService.deleteSession(sessionId = Session(session_id = session))
        }
    }
}
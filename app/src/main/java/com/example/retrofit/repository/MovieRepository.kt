package com.example.retrofit.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.model.api.LoginApprove
import com.example.retrofit.model.api.Movie
import com.example.retrofit.model.api.Token
import com.example.retrofit.model.database.MovieDao
import com.example.retrofit.model.database.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MovieRepository(application: Application) {

    private val context = application
    private var list: List<Movie>? = null
    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).movieDao()
    private val apiService = RetrofitService.getInstance()
//    private val _sessionId = MutableLiveData<String>()

    suspend fun getAllMovieList():  List<Movie> {
        list = withContext(Dispatchers.IO) {
            try {
                val response = RetrofitService.getPostApi().getCoroutinesMovieList(apiKey = "02c64fae28c1003e5a0725abd7c2e518")
                if (response.isSuccessful) {
                    val movies = response.body()
                    val results = movies?.results
                    if (!results.isNullOrEmpty()) {
                        movieDao.insertAll(results)
                    }
                    results
                } else {
                    movieDao.getAll()
                }
            } catch (e: Exception) {
                movieDao.getAll()
            }
        }
    return list?: emptyList()
    }

   suspend fun login(data: LoginApprove): String {
            var session: String = ""
            val responseGet = apiService.getToken()
            if (responseGet.isSuccessful) {

                val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                sharedPreference.edit().putString("TOKEN", responseGet.body()?.request_token).apply()

                val loginApprove = LoginApprove(
                    username = data.username,
                    password = data.password,
                    request_token = responseGet.body()?.request_token as String
                )
                val responseApprove = apiService.approveToken(loginApprove = loginApprove)
                if (responseApprove.isSuccessful) {
                    val response =
                        apiService.createSession(token = responseApprove.body() as Token)
                    if (response.isSuccessful) {
                        session = response.body()?.session_id as String
                    }
                } else {
                    Toast.makeText(context, "Неверные данные", Toast.LENGTH_SHORT).show()
                }
            }
            return session
    }
}
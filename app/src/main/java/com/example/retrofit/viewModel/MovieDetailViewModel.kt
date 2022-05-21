package com.example.retrofit.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.retrofit.model.api.PostMovie
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.model.api.Movie
import com.example.retrofit.model.database.MovieDao
import com.example.retrofit.model.database.MovieDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).movieDao()
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _addFavoriteState = MutableLiveData<Boolean>()
    val addFavoriteState: LiveData<Boolean>
        get() = _addFavoriteState
    private val _compose = MutableLiveData<Boolean>()
    val compose: LiveData<Boolean>
        get() = _compose

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            val movieFL = withContext(Dispatchers.IO) {
                val result=movieDao.getMovieById(movieId)
                result
            }
            _movie.value=movieFL
        }
    }

    fun composeFavorite(session: String, id: Int) {
        viewModelScope.launch {
            val response = RetrofitService.getInstance().getFavoriteMovie(session_id = session, id=id)
            if (response.isSuccessful) {
                _compose.value = response.body()?.favorite == true
            }
        }
    }

    fun addFavorite(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            val postMovie = PostMovie(media_id = movieId, favorite = true)
            val response = RetrofitService.getPostApi().addFavorite(
                session_id = sessionId,
                postMovie = postMovie
            )
            _addFavoriteState.value = response.isSuccessful
        }
    }

    fun deleteFavorites(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            val postMovie = PostMovie(media_id = movieId, favorite = false)
            val response = RetrofitService.getPostApi().addFavorite(
                session_id = sessionId,
                postMovie = postMovie
            )
            _addFavoriteState.value = response.isSuccessful
        }
    }
}
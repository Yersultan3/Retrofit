package com.example.retrofit.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.retrofit.model.Event
import com.example.retrofit.model.api.Movie
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.model.database.MovieDao
import com.example.retrofit.model.database.MovieDatabase
import com.example.retrofit.model.database.MovieDatabase.Companion.getDatabase
import com.example.retrofit.repository.MovieRepository
import com.example.retrofit.view.adapter.MovieAdapter
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MovieListViewModel(
    application: Application
): AndroidViewModel(application), CoroutineScope {

    private val context = application
    private val movieDao: MovieDao = getDatabase(application).movieDao()
    private val job: Job = Job()
    private val moviesRepository = MovieRepository(application)



    override val coroutineContext: CoroutineContext
         get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData



    private val _openDetail = MutableLiveData<Event<Movie>>()
    val openDetail: LiveData<Event<Movie>>
        get() = _openDetail


    fun getMoviesCoroutine() {
        launch {
            _liveData.value = State.ShowLoading
            moviesRepository.getAllMovieList()
            _liveData.value = State.Result(moviesRepository.getAllMovieList())
            _liveData.value = State.HideLoading
        }
    }

//    fun getMoviesCoroutine() {
//        launch {
//            _liveData.value = State.ShowLoading
//
//            val list = withContext(Dispatchers.IO) {
//                try {
//                    val response = RetrofitService.getPostApi().getCoroutinesMovieList(apiKey = "02c64fae28c1003e5a0725abd7c2e518")
//                    if (response.isSuccessful) {
////                        val result = response.body()
//                        val movies = response.body()
//                        val results = movies?.results
//                        if (!results.isNullOrEmpty()) {
//                            movieDao.insertAll(results)
//                        }
//                        results
//                    } else {
//                        movieDao.getAll()
//                    }
//                } catch (e: Exception) {
//                    movieDao.getAll()
//                }
//            }
//            _liveData.value = State.Result(list)
//            _liveData.value = State.HideLoading
//        }
//    }

    val recyclerViewItemClickListener = object: MovieAdapter.RecyclerViewItemClick {
        override fun itemClick(item: Movie) {
            _openDetail.value = Event(item)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    sealed class State {
        object ShowLoading: State()
        object HideLoading: State()
        data class Result(val list: List<Movie>?): State()
    }
}
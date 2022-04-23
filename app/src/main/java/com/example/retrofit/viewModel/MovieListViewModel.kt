package com.example.retrofit.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.model.Event
import com.example.retrofit.model.api.Movie
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.model.database.MovieDao
import com.example.retrofit.model.database.MovieDatabase
import com.example.retrofit.view.adapter.MovieAdapter
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MovieListViewModel(
    private val context: Context
): ViewModel(), CoroutineScope {

    private val movieDao: MovieDao
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
         get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData

    private val _openDetail = MutableLiveData<Event<Movie>>()
    val openDetail: LiveData<Event<Movie>>
        get() = _openDetail

    init {
        getMoviesCoroutine()
        movieDao = MovieDatabase.getDatabase(context).movieDao()
    }

    fun getMoviesCoroutine() {
        launch {
            _liveData.value = State.ShowLoading

            val list = withContext(Dispatchers.IO) {
                try {

                    val response = RetrofitService.getPostApi().getCoroutinesMovieList(apiKey = "02c64fae28c1003e5a0725abd7c2e518")

                    if (response.isSuccessful) {
//                        val result = response.body()
                        val movies = response.body()
                        val results = movies?.results as List<Movie>
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
            _liveData.value = State.Result(list)
            _liveData.value = State.HideLoading

//            val response = RetrofitService.getPostApi().getCoroutinesMovieList(apiKey = "02c64fae28c1003e5a0725abd7c2e518")
//            if (response.isSuccessful) {
//                val movies = response.body()
//                val results = movies?.results as List<Movie>
//                _liveData.value = State.Result(results)
//                _liveData.value = State.HideLoading
//            }
        }
    }

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
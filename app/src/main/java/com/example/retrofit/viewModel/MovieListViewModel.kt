package com.example.retrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.model.Event
import com.example.retrofit.model.Movie
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.view.adapter.MovieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieListViewModel: ViewModel(), CoroutineScope {

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
    }

    fun getMoviesCoroutine() {
        launch {
            _liveData.value = State.ShowLoading
            val response = RetrofitService.getPostApi().getCoroutinesMovieList(apiKey = "02c64fae28c1003e5a0725abd7c2e518")
            if (response.isSuccessful) {
                val movies = response.body()
                val results = movies?.results as List<Movie>
                _liveData.value = State.Result(results)
                _liveData.value = State.HideLoading
            }
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
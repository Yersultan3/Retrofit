package com.example.retrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.model.api.Movie
import com.example.retrofit.model.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel: ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _liveData = MutableLiveData<Movie>()
    val liveData: LiveData<Movie>
        get() = _liveData

    fun getCoroutinesMovieById(id: Int) {
        launch {
            val response = RetrofitService.getPostApi().getMovieByIdCoroutines(id,
                apiKey = "02c64fae28c1003e5a0725abd7c2e518")
            if (response.isSuccessful) {
                _liveData.postValue(response.body())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
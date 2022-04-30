package com.example.retrofit.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.retrofit.model.Event
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.model.api.Movie
import com.example.retrofit.model.api.MovieApi
import com.example.retrofit.model.api.Session
import com.example.retrofit.view.adapter.MovieAdapter
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModelFavorites(application: Application) : AndroidViewModel(application) {

    private val context = application
    private val apiService = RetrofitService.getInstance()
//    private val repository = Repository(context)

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

//    private val _loadingState = MutableLiveData<State>()
//    val loadingState: LiveData<State>
//        get() = _loadingState

    private val _openDetail = MutableLiveData<Event<Movie>>()
    val openDetail: LiveData<Event<Movie>>
        get() = _openDetail



    fun getFavorites(session: String, page: Int) {
        viewModelScope.launch {
            val response = apiService.getFavorites(
                session_id = session,
                page = page
            )
            if (response.isSuccessful) {
                _movies.value = response.body()?.results
            } else {
                Toast.makeText(
                    context,
                    "Требуется авторизация",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val recyclerViewItemClickListener = object: MovieAdapter.RecyclerViewItemClick {
        override fun itemClick(item: Movie) {
            _openDetail.value = Event(item)
        }
    }


    fun deleteSession(session: String) {
        viewModelScope.launch {
            apiService.deleteSession(sessionId = Session(session_id = session))
        }
    }
//
//    sealed class State {
//        object ShowLoading: State()
//        object HideLoading: State()
//        data class Result(val list: List<Movie>?): State()
//    }
}
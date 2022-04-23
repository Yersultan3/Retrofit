package com.example.retrofitexample.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.retrofit.model.api.Movie
import com.example.retrofit.viewModel.MovieListViewModel

class MovieListViewModelObserver(
    private val context: Context,
    private val viewModel: MovieListViewModel,
    private val viewLifecycleOwner: LifecycleOwner,

    private val openDetail: ((post: Movie) -> Unit),
    private val liveData: ((state: MovieListViewModel.State) -> Unit)
) {

    init {
        observeViewModel()
    }

    private fun observeViewModel() {
        liveData.apply {
            viewModel.liveData.observe(
                viewLifecycleOwner
            ) {
                this.invoke(it)
            }
        }

        openDetail.apply {
            viewModel.openDetail.observe(
                viewLifecycleOwner
            ) {
                it.getContentIfNotHandled()?.let {
                    this.invoke(it)
                }
            }
        }
    }

}
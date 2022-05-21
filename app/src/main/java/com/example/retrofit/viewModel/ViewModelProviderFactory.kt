package com.example.retrofit.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//class ViewModelProviderFactory(private val context: Context) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return modelClass.getConstructor(Context::class.java).newInstance(context)
//    }
//}

//class ViewModelProviderFactory(val app: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return MovieListViewModel(app) as T
//        }
//        throw IllegalArgumentException("Unable to construct viewmodel")
//    }
//}
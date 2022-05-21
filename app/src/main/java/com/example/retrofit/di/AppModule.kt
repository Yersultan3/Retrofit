package com.example.retrofit.di

import android.content.Context
import com.example.retrofit.model.RetrofitService
import com.example.retrofit.model.database.MovieDao
import com.example.retrofit.model.database.MovieDatabase
import com.example.retrofit.repository.MovieRepository
import org.koin.dsl.module


val networkModule = module {
    single { getRetrofitService() }
}

val daoModule = module {
    single { getPostDao(context = get() )}
}

val repositoryModule = module {
    single { MovieRepository( get() )}
}


private fun getRetrofitService(): RetrofitService = RetrofitService
private fun getPostDao(context: Context): MovieDao = MovieDatabase.getDatabase(context).movieDao()

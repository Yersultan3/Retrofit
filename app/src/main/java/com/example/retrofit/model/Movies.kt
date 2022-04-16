package com.example.retrofit.model

import com.example.retrofit.model.Movie

data class Movies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)



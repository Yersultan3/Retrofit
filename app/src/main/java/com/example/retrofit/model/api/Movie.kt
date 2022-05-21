package com.example.retrofit.model.api

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
) : Parcelable

//fun List<Movie>.asDomainModel(): List<Movie> {
//    return map {
//        Movie(
//            id = it.id,
//            adult = it.adult,
//            backdrop_path = it.backdrop_path,
//            original_language = it.original_language,
//            original_title= it.original_title,
//            overview = it.overview,
//            popularity = it.popularity,
//            poster_path = it.poster_path,
//            release_date = it.release_date,
//            title = it.title,
//            video = it.video,
//            vote_average = it.vote_average,
//            vote_count = it.vote_count
//        )
//    }
//}
package com.example.retrofit.model.api

import com.example.retrofit.model.CreateToken
import com.example.retrofit.model.ValidateWithLogin
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieApi {

    @GET("movie/top_rated")
    fun getMovieList(@Query("api_key")apiKey:String): Call<Movies>

    @GET("movie/top_rated")
   suspend fun getCoroutinesMovieList(@Query("api_key")apiKey:String): Response<Movies>

    @GET("movie/{movie_id}")
    fun getMovieByIdCoroutines(@Path("/movie/{movie_id}") id: Int,
                    @Query("api_key") apiKey:String): Response<Movie>

    @GET("authentication/token/new")
    fun createToken(
        @Query("api_key") apiKey:String
    ): Response<CreateToken>

    @POST("/authentication/token/validate_with_login")
    fun validateWithLogin(
        @Query("api_key") apiKey:String,
        @Body body : RequestBody
    ): Response<ValidateWithLogin>


}
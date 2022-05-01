package com.example.retrofit.model.api

import com.example.myfilms.data.models.Token
import com.example.retrofit.model.CreateToken
import com.example.retrofit.model.ValidateWithLogin
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieApi {

    companion object {
        private var SESSION_ID = ""
        private var API_KEY = "02c64fae28c1003e5a0725abd7c2e518"
        private var PARAMS_LANGUAGE = "ru"
        private var SORT_BY_POPULARITY = "popularity.desc"
        private var PARAMS_PAGE = 1
    }

    @GET("authentication/token/new")
    suspend fun getToken(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<Token>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body sessionId: Session
    )

    @POST("authentication/token/validate_with_login")
    suspend fun approveToken(
        @Query("api_key") apiKey: String = API_KEY,
        @Body loginApprove: LoginApprove
    ): Response<Token>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body token: Token
    ): Response<Session>

    @GET("movie/popular")
    fun getMovieList(@Query("api_key")apiKey: String): Call<Movies>

    @GET("movie/popular")
   suspend fun getCoroutinesMovieList(@Query("api_key")apiKey: String): Response<Movies>

    @GET("movie/{movie_id}")
    fun getMovieByIdCoroutines(@Path("/movie/{movie_id}") id: Int,
                    @Query("api_key") apiKey: String): Response<Movie>

    @GET("authentication/token/new")
    fun createToken(
        @Query("api_key") apiKey: String
    ): Response<CreateToken>

    @POST("/authentication/token/validate_with_login")
    fun validateWithLogin(
        @Query("api_key") apiKey: String,
        @Body body: RequestBody
    ): Response<ValidateWithLogin>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorites(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
        @Query("language") language: String = PARAMS_LANGUAGE,
        @Query("sort_by") sort_by: String = SORT_BY_POPULARITY,
        @Query("page") page: Int = PARAMS_PAGE
    ): Response<Movies>

    @GET("movie/{movie_id}/account_states")
    suspend fun getFavoriteMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
    ): Response<CheckFavourites>

    @POST("account/{account_id}/favorite")
    suspend fun addFavorite(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
        @Body postMovie: PostMovie
    ): Response<FavoriteResult>
}
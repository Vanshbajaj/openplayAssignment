package com.example.openplayassigment.data

import com.example.openplayassigment.data.response.MovieDataClass
import com.example.openplayassigment.data.response.MovieItemResponse
import com.example.openplayassigment.data.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("/")
    suspend fun getPopularMovies(@Query("s") query: String,@Query("apikey") apiKey: String): MovieDataClass

    @GET("/")
    suspend fun getMovieDetails(@Query("t") title: String, @Query("apikey") apiKey: String): MovieItemResponse
}

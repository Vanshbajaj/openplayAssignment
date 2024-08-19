package com.example.openplayassigment.data

import com.example.openplayassigment.data.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("search/movie")
    suspend fun getMovieDetails(@Query("api_key") apiKey: String,@Query("query") query: String,): MovieResponse
}

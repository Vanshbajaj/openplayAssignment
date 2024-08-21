package com.example.openplayassigment.data

import com.example.openplayassigment.data.response.MovieItemResponse
import com.example.openplayassigment.data.response.Search

interface MovieRepository {
    suspend fun fetchMovies(search: String): List<Search>
    suspend fun getMovieDetails(movieId: String): MovieItemResponse
}
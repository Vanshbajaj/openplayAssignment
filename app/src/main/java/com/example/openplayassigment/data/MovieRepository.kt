package com.example.openplayassigment.data

import com.example.openplayassigment.data.local.MovieEntity

interface MovieRepository {
    suspend fun fetchMovies(): List<MovieEntity>
    suspend fun getMovieDetails(movieId: String): MovieEntity?
}
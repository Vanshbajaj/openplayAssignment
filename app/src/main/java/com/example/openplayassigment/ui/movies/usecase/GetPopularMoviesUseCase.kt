package com.example.openplayassigment.ui.movies.usecase

import com.example.openplayassigment.data.MovieRepository
import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.data.response.MovieDataClass
import com.example.openplayassigment.data.response.MovieItemResponse
import com.example.openplayassigment.data.response.Search
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend fun fetchMovies(search: String): List<Search> {
        return repository.fetchMovies(search)
    }

    suspend fun getMovieDetails(movieId: String): MovieItemResponse {
        return repository.getMovieDetails(movieId)
    }
}

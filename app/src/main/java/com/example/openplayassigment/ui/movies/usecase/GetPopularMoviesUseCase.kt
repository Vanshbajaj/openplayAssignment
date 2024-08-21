package com.example.openplayassigment.ui.movies.usecase

import com.example.openplayassigment.data.MovieRepository
import com.example.openplayassigment.data.MovieRepositoryImpl
import com.example.openplayassigment.data.local.MovieEntity
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend fun fetchMovies(): List<MovieEntity> {
        return repository.fetchMovies()
    }

    suspend fun getMovieDetails(movieId: String): MovieEntity? {
        return repository.getMovieDetails(movieId)
    }
}

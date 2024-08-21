package com.example.openplayassigment.ui.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.ui.movies.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val _movie = MutableStateFlow<MovieEntity?>(null)
    val movie: StateFlow<MovieEntity?> = _movie

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val movieDetails = moviesUseCase.getMovieDetails(movieId)
                _movie.value = movieDetails
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}







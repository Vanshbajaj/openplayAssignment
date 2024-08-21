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
class MovieListViewModel @Inject constructor(
    private val moviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    // Expose the UI state as a read-only StateFlow
    private val _movies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movies: StateFlow<List<MovieEntity>> get() = _movies

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val movieList = moviesUseCase.fetchMovies()
                _movies.value = movieList
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }


}

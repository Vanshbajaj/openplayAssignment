package com.example.openplayassigment.ui.movies.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.ui.movies.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val moviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

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
    //added search functionality
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query



    }

    val filteredMovies: StateFlow<List<MovieEntity>> = combine(
        _movies,
        _searchQuery
    ) { movies, query ->
        if (query.isBlank()) {
            movies
        } else {
            movies.filter { it.title.contains(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}

package com.example.openplayassigment.ui.movies.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.data.response.MovieDataClass
import com.example.openplayassigment.data.response.Search
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

    private val _movies = MutableStateFlow<List<Search>>(emptyList())
    val movies: StateFlow<List<Search>> get() = _movies
        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading

        private val _error = MutableStateFlow<String?>(null)
        val error: StateFlow<String?> = _error

        private val _searchQuery = MutableStateFlow("")
        val searchQuery: StateFlow<String> = _searchQuery

        fun onSearchQueryChanged(newQuery: String) {
            _searchQuery.value = newQuery
            searchMovies(newQuery)
        }

        private fun searchMovies(query: String) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    _movies.value = moviesUseCase.fetchMovies(query)
                    _error.value = null
                } catch (e: Exception) {
                    _error.value = e.message
                    _movies.value = emptyList() // Ensure movies is never null
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }



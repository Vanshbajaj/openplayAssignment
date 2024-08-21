package com.example.openplayassigment.data

import com.example.openplayassigment.data.local.MovieDao
import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.data.response.MovieItemResponse
import com.example.openplayassigment.data.response.Search
import com.example.openplayassigment.utils.AppConstants
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun fetchMovies(search: String): List<Search> {
        // check for local List<MovieEntity>
        val localMovieList = movieDao.getMovies()
//        if (localMovieList.isNullOrEmpty()) {
            val response = apiService.getPopularMovies(search,AppConstants.API_KEY)
            if (response.search.isEmpty()) {
                return emptyList()
            }
            // Convert MovieResponse to List<MovieEntity>
            val movieList: ArrayList<MovieEntity> = arrayListOf()
//            response.search.forEach {
//                movieList.add(it.toMovieEntity())
//            }
//            saveMoviesList(movieList)
            return response.search
//        }
//        else {
//            return localMovieList.
//        }
    }

    private suspend fun saveMoviesList(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }

    override suspend fun getMovieDetails(movieId: String): MovieItemResponse {
        val response = fetchMovieDetailsFromNetwork(movieId)
        return response

    }

    private suspend fun fetchMovieDetailsFromNetwork(movieId: String): MovieItemResponse {
        val response = apiService.getMovieDetails(movieId,AppConstants.API_KEY)
       return response
    }

    private suspend fun saveMovieDetails(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }
}




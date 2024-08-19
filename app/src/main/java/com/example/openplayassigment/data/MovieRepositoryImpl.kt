package com.example.openplayassigment.data

import com.example.openplayassigment.data.local.MovieDao
import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.utils.AppConstants
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun fetchMovies(): List<MovieEntity> {
        // check for local List<MovieEntity>
        val localMovieList = movieDao.getMovies()
        if (localMovieList.isNullOrEmpty()) {
            val response = apiService.getPopularMovies(AppConstants.API_KEY)
            if (response.movies.isEmpty()) {
                return emptyList()
            }
            // Convert MovieResponse to List<MovieEntity>
            val movieList: ArrayList<MovieEntity> = arrayListOf()
            response.movies.forEach {
                movieList.add(it.toMovieEntity())
            }
            saveMoviesList(movieList)
            return movieList
        } else {
            return localMovieList
        }
    }

    private suspend fun saveMoviesList(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }

    override suspend fun getMovieDetails(movieId: String): MovieEntity {
        val movie = movieDao.getMovieById(movieId)
        if (movie != null) {
            return movie
        } else {
            val response = fetchMovieDetailsFromNetwork(movieId)
            saveMovieDetails(response)
            return response
        }
    }

    private suspend fun fetchMovieDetailsFromNetwork(movieId: String): MovieEntity {
        val response = apiService.getMovieDetails("8f757ea1c6e3dc2deef92fb4682d7e78", movieId)
        return MovieEntity(
            id = response.movies[0].id.toString(),
            title = response.movies[0].title,
            overview = response.movies[0].overview,
            posterPath = response.movies[0].posterPath ?: "",
            releaseDate = response.movies[0].releaseDate ?: "",
            voteAverage = response.movies[0].voteAverage ?: 0.0
        )
    }

    private suspend fun saveMovieDetails(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }
}




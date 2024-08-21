package com.example.openplayassigment.data

import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.data.response.Movie

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id.toString(),
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}
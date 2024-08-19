package com.example.openplayassigment.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.openplayassigment.utils.AppConstants

@Entity(tableName = AppConstants.TABLE_MOVIES)
data class MovieEntity(
    @PrimaryKey val id: String,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
)


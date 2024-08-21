package com.example.openplayassigment.data.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MovieResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var movies: List<Movie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)
package com.example.openplayassigment.data.response


import com.google.gson.annotations.SerializedName

data class MovieDataClass(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: List<Search>,
    @SerializedName("totalResults")
    val totalResults: String
)
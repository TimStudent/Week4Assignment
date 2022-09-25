package com.example.week4assignment.model

import com.google.gson.annotations.SerializedName

data class MoviesData(
    @SerializedName("results")
    val movieDomain: List<MovieDomain?>? = null,
)
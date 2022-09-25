package com.example.week4assignment.model

import com.google.gson.annotations.SerializedName

data class TrailersData(
    @SerializedName("results")
    val trailerDomain: List<TrailerDomain?>? = null
)

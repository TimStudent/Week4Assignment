package com.example.week4assignment.model

import com.google.gson.annotations.SerializedName

data class DetailsData(
    @SerializedName("adult")
    val adult : Boolean = false,
    @SerializedName("genres")
//    var genres: ArrayList<String> = ArrayList(),
    var genres: List<Genre> = emptyList(),
    @SerializedName("popularity")
    var popularity : Double = 0.0,
    @SerializedName("homepage")
    var homepage : String = "",
    @SerializedName("id")
    var id : Int = 0,
    @SerializedName("runtime")
    var runtime : Int = 0,
    @SerializedName("")
    val detailDomain: List<DetailDomain?>?=null,
    @SerializedName("")
    val detailsDomain: DetailDomain?=null
)

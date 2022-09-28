package com.example.week4assignment.utils

import com.example.week4assignment.model.room.DetailData
import com.example.week4assignment.model.room.MovieData
import com.example.week4assignment.model.room.TrailerData
import java.lang.Exception

sealed class UIState {
    object LOADING: UIState()
    data class SUCCESS(
        val success: List<MovieData>? = null,
        val success2:List<TrailerData>? = null,
        val success3: DetailData? = null
    ) : UIState()
    data class ERROR(val error: Exception) : UIState()
}
package com.example.week4assignment.utils

import com.example.week4assignment.model.room.MovieData
import com.example.week4assignment.model.room.TrailerData
import java.lang.Exception

sealed class UIState {
    object LOADING: UIState()
    data class SUCCESS(val success: List<MovieData>?) : UIState()
    data class SUCCESS2(val success: List<TrailerData>?): UIState()
    data class ERROR(val error: Exception) : UIState()
}
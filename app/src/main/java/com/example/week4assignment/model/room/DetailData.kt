package com.example.week4assignment.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.week4assignment.model.DetailDomain
import com.example.week4assignment.model.DetailsData
import com.example.week4assignment.model.Genre

@Entity(tableName = "details_table")

data class DetailData(
    @ColumnInfo var adult: Boolean = false,
    @ColumnInfo var genres : List<Genre> = emptyList(),
    @ColumnInfo var popularity: Double = 0.0,
    @ColumnInfo var homepage : String = "",
    @ColumnInfo var id : Int = 0,
    @ColumnInfo var runtime: Int = 0,
    @ColumnInfo var expand: Boolean = false
)

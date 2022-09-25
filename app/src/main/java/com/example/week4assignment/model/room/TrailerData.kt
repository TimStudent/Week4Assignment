package com.example.week4assignment.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.week4assignment.model.TrailerDomain

@Entity(tableName = "Trailer")
data class TrailerData(
    @ColumnInfo
    val iso31661: String? = null,
    @ColumnInfo
    val iso6391: String? = null,
    @ColumnInfo
    val name: String? = null,
    @ColumnInfo
    val key: String? = null,
    @ColumnInfo
    val site: String? = null,
    @ColumnInfo
    val size: Int? = null,
    @ColumnInfo
    val type: String? = null,
    @ColumnInfo
    val official: Boolean? = null,
    @ColumnInfo
    val publishedAt: String? = null,
    @PrimaryKey()
    val id: String? = null
)

fun List<TrailerDomain?>?.mapToTrailerList():List<TrailerData>?=this?.map {
    TrailerData(
        iso6391 = it?.iso6391,
        iso31661 = it?.iso31661,
        name = it?.name,
        key = it?.key,
        site = it?.site,
        size = it?.size,
        type = it?.type,
        official = it?.official,
        publishedAt = it?.publishedAt,
        id = it?.id
    )
}

package com.example.week4assignment.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.week4assignment.model.MovieDomain

@Entity(tableName = "Movie_List")
@TypeConverters(typeConverter::class)
data class MovieData (
    @ColumnInfo var adult: Boolean = false,
    @ColumnInfo var backdrop_path: String = "",
    //@ColumnInfo var genre_ids: ArrayList<Int> = ArrayList(),
    @PrimaryKey var id : Int = 0,
    @ColumnInfo var original_language : String = "",
    @ColumnInfo var original_title : String = "",
    @ColumnInfo var overview : String = "",
    @ColumnInfo var popularity : Double = 0.0,
    @ColumnInfo var poster_path : String = "",
    @ColumnInfo var release_date : String = "",
    @ColumnInfo var title : String = "",
    @ColumnInfo var vote_average : Double = 0.0,
    @ColumnInfo var vote_count : Int = 0,
    var expand : Boolean = false
)

fun List<MovieDomain?>?
        .mapToMovieList(): List<MovieData>? =
    this?.map {
        MovieData(
            adult = it?.adult ?: false,
            backdrop_path = it?.backdrop_path ?: "Invalid Backdrop",
            //genre_ids = it?.genre_ids ?: ArrayList(),
            id = it?.id ?: 0,
            original_language = it?.original_language ?: "No Language Found",
            original_title = it?.original_title ?: "No Title Found",
            overview = it?.overview ?: "Nothing found",
            popularity = it?.popularity ?: 0.0,
            poster_path = it?.poster_path ?: "Poster Path Not Found",
            release_date = it?.release_date ?: "Release Date Not Found",
            title = it?.title ?: "Title Not Found",
            vote_average = it?.vote_average ?: 0.0,
            vote_count = it?.vote_count ?: 0,
            expand = false
        )
    }



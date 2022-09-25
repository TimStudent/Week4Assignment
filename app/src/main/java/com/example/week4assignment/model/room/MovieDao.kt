package com.example.week4assignment.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie: MovieData)

    @Query("SELECT * FROM Movie_List ORDER BY id ASC")
    fun readAllData(): LiveData<List<MovieData>>

    @Query("SELECT * FROM Movie_List WHERE adult = 'true' ORDER BY id ASC")
    fun readAdult(): LiveData<List<MovieData>>

    @Query("SELECT * FROM Movie_List WHERE adult = 'false' ORDER BY id ASC")
    fun readNotAdult(): LiveData<List<MovieData>>

}
package com.example.week4assignment.model.room

import androidx.lifecycle.LiveData

class MovieDataRepository(private val movieDao: MovieDao) {

    val readAllData: LiveData<List<MovieData>> = movieDao.readAllData()
    val readAdultData: LiveData<List<MovieData>> = movieDao.readAdult()
    val readNonAdultData: LiveData<List<MovieData>> = movieDao.readNotAdult()

    suspend fun addMovie(movie: MovieData){
        movieDao.addMovie(movie)
    }
}
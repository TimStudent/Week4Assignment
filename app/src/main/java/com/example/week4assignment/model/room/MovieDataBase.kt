package com.example.week4assignment.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieData::class], version = 1, exportSchema = false)
@TypeConverters(typeConverter::class)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object{
        @Volatile
        private var INSTANCE: MovieDataBase? = null

        fun getDatabase(context: Context): MovieDataBase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDataBase::class.java,
                    "Movie_List"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
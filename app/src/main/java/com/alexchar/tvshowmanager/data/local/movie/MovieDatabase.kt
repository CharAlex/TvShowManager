package com.alexchar.tvshowmanager.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexchar.tvshowmanager.data.local.movie.MovieDao
import com.alexchar.tvshowmanager.data.local.movie.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object{
        val DATABASE_NAME: String = "movie_db"
    }
}
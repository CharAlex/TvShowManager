package com.alexchar.tvshowmanager.data.local.movie

import androidx.room.*
import com.alexchar.tvshowmanager.data.local.movie.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Insert(entity = MovieEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movieList: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: Int): MovieEntity?

    @Query("SELECT * FROM movies ORDER BY created_at DESC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovie(id: Int)
}
package com.alexchar.tvshowmanager.data.local.movie

import com.alexchar.tvshowmanager.data.local.movie.model.MovieEntity
import com.alexchar.tvshowmanager.data.local.movie.model.toDomain
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.util.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalSource @Inject constructor(
    private val movieDao: MovieDao
) : IMovieLocalSource {

    override suspend fun insertAll(movies: List<MovieEntity>?) : ViewState<Unit> {
        return try {
            movieDao.insertAll(movies!!)
            ViewState.Success(Unit)
        } catch (e: Exception) {
            ViewState.Error(e.message)
        }

    }

    override fun getMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { movies ->
            movies.map { it.toDomain() }
        }
    }
}

interface IMovieLocalSource {
    suspend fun insertAll(movies: List<MovieEntity>?): ViewState<Unit>
    fun getMovies(): Flow<List<Movie>>
}
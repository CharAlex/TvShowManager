package com.alexchar.tvshowmanager.domain.movie

import com.alexchar.tvshowmanager.MovieListQuery
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.util.ViewState
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<List<Movie>>

    suspend fun syncMovies(
        first: Int, after: Int
    ): ViewState<Unit>

    suspend fun createMovie(movie: MovieListQuery.Node): ViewState<Unit>
}
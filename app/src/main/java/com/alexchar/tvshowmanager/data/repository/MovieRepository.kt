package com.alexchar.tvshowmanager.data.repository

import com.alexchar.tvshowmanager.data.local.movie.IMovieLocalSource
import com.alexchar.tvshowmanager.data.local.movie.model.toEntity
import com.alexchar.tvshowmanager.MovieListQuery
import com.alexchar.tvshowmanager.data.remote.movie.IMovieRemoteSource
import com.alexchar.tvshowmanager.domain.movie.IMovieRepository
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.util.ViewState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteSource: IMovieRemoteSource,
    private val movieLocalSource: IMovieLocalSource
) : IMovieRepository {

    override fun getMovies(): Flow<List<Movie>> = movieLocalSource.getMovies()

    override suspend fun syncMovies(first: Int, after: Int): ViewState<Unit>  {
        val response = movieRemoteSource.queryMovieList(first, after)

        val movieEntities = response.data?.movies?.edges?.mapNotNull {
            it?.node?.toEntity()
        }
        if (movieEntities != null) {
            return movieLocalSource.insertAll(movieEntities)
        }

        return ViewState.Error("Insertion fail")
    }
}
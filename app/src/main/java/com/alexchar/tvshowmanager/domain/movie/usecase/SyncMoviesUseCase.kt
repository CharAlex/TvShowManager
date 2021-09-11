package com.alexchar.tvshowmanager.domain.movie.usecase

import com.alexchar.tvshowmanager.domain.movie.IMovieRepository
import com.alexchar.tvshowmanager.domain.util.ViewState
import javax.inject.Inject

interface ISyncMoviesUseCase {
    suspend fun execute(first: Int, after: Int): ViewState<Unit>
}

class SyncMoviesUseCase @Inject constructor(
    private val movieRepository: IMovieRepository
) : ISyncMoviesUseCase {
    override suspend fun execute(first: Int, after: Int): ViewState<Unit> = movieRepository.syncMovies(first, after)
}

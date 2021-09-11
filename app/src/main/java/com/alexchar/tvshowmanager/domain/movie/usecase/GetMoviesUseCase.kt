package com.alexchar.tvshowmanager.domain.movie.usecase

import com.alexchar.tvshowmanager.domain.movie.IMovieRepository
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IGetMoviesUseCase {
    fun execute(): Flow<List<Movie>>
}

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: IMovieRepository
) : IGetMoviesUseCase {
    override fun execute(): Flow<List<Movie>> =
        movieRepository.getMovies()
}
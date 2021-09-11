package com.alexchar.tvshowmanager.domain.movie.usecase

import com.alexchar.tvshowmanager.MovieListQuery
import com.alexchar.tvshowmanager.domain.movie.IMovieRepository
import com.alexchar.tvshowmanager.domain.util.ViewState
import javax.inject.Inject

interface ICreateMovieUseCase {
    suspend fun execute(movie: MovieListQuery.Node): ViewState<Unit>
}

class CreateMovieUseCase @Inject constructor(
    private val movieRepository: IMovieRepository
) : ICreateMovieUseCase {
    override suspend fun execute(movie: MovieListQuery.Node): ViewState<Unit> {
        return movieRepository.createMovie(movie)
    }
}
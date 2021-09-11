package com.alexchar.tvshowmanager.domain

import com.alexchar.tvshowmanager.MovieListQuery
import com.alexchar.tvshowmanager.domain.movie.IMovieRepository
import com.alexchar.tvshowmanager.domain.movie.usecase.CreateMovieUseCase
import com.alexchar.tvshowmanager.domain.util.Constants
import com.alexchar.tvshowmanager.domain.util.ViewState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class CreateMovieUseCaseTest {

    private val movieRepo = mockk<IMovieRepository>()
    private val useCase = CreateMovieUseCase(movieRepo)

    @ExperimentalCoroutinesApi
    @Test
    fun `given repo create succeeds, when use case executes, then success is returned`() =
        runBlockingTest {
            // given
            val movie: MovieListQuery.Node = mockk()
            val result = ViewState.Success(Unit)

            coEvery {
                movieRepo.createMovie(movie)
            } returns result


            // when, then
            Assert.assertEquals(useCase.execute(movie), result)
            coVerify { movieRepo.createMovie(movie)  }

        }

    @ExperimentalCoroutinesApi
    @Test
    fun `given repo create fails, when use case executes, then error is returned`() =
        runBlockingTest {
            // given
            val movie: MovieListQuery.Node = mockk()
            val result = ViewState.Error<String>(Constants.CREATE_MOVIE_ERROR_CODE)

            coEvery {
                movieRepo.createMovie(movie)
            } returns result as ViewState<Unit>


            // when, then
            Assert.assertEquals(useCase.execute(movie), result)
            coVerify { movieRepo.createMovie(movie)  }

        }

}
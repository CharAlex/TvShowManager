package com.alexchar.tvshowmanager.domain

import com.alexchar.tvshowmanager.domain.movie.IMovieRepository
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.movie.usecase.GetMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class GetMoviesUseCaseTest {

    private val movieRepo = mockk<IMovieRepository>()
    private val useCase = GetMoviesUseCase(movieRepo)

    @ExperimentalCoroutinesApi
    @Test
    fun `given repo returns a list, when use cases is executed, then the list is returned`() =
        runBlockingTest {
            // given
            val movieList = emptyList<Movie>()
            every { movieRepo.getMovies() } returns flowOf(movieList)

            // when, then
            useCase.execute().collect {
                Assert.assertEquals(it, movieList)
            }
            verify { movieRepo.getMovies() }
        }

}
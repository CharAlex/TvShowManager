package com.alexchar.tvshowmanager.domain

import com.alexchar.tvshowmanager.domain.movie.IMovieRepository
import com.alexchar.tvshowmanager.domain.movie.usecase.SyncMoviesUseCase
import com.alexchar.tvshowmanager.domain.util.Constants
import com.alexchar.tvshowmanager.domain.util.ViewState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class SyncMoviesUseCaseTest {

    private val movieRepo = mockk<IMovieRepository>()
    private val useCase = SyncMoviesUseCase(movieRepo)

    @ExperimentalCoroutinesApi
    @Test
    fun `given repo sync succeeds, when use case executes, then success is returned`() =
        runBlockingTest {
            // given
            val result = ViewState.Success(Unit)
            coEvery { movieRepo.syncMovies(any(), any()) } returns result

            // when, then
            Assert.assertEquals(useCase.execute(0, 0), result)
            coVerify { movieRepo.syncMovies(any(), any()) }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `given repo sync fails, when use case executes, then error is returned`() =
        runBlockingTest {
            // given
            val result = ViewState.Error<String>(Constants.INSERTION_FAIL_CODE)

            coEvery { movieRepo.syncMovies(any(), any()) } returns result as ViewState<Unit>


            // when, then
            Assert.assertEquals(useCase.execute(0, 0), result)
            coVerify { movieRepo.syncMovies(any(), any()) }
        }

}
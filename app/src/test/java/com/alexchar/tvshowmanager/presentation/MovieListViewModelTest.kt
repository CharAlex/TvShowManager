package com.alexchar.tvshowmanager.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.movie.usecase.IGetMoviesUseCase
import com.alexchar.tvshowmanager.domain.movie.usecase.ISyncMoviesUseCase
import com.alexchar.tvshowmanager.domain.util.Constants
import com.alexchar.tvshowmanager.domain.util.Constants.Companion.INSERTION_FAIL_CODE
import com.alexchar.tvshowmanager.domain.util.ViewState
import com.alexchar.tvshowmanager.main.MainCoroutineRule
import com.alexchar.tvshowmanager.main.emitsValueEventually
import com.alexchar.tvshowmanager.presentation.main.list.MovieListViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class MovieListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = MainCoroutineRule()

    private val syncMoviesUseCase = mockk<ISyncMoviesUseCase>()
    private val getMoviesUseCase = mockk<IGetMoviesUseCase>()

    @ExperimentalCoroutinesApi
    @Test
    fun `given viewModel initializes, when getMoviesUseCase is executed, view model movies live data is updated`() =
        runBlockingTest {
            // given
            val movieList = emptyList<Movie>()
            every { getMoviesUseCase.execute() } returns flowOf(movieList)
            coEvery { syncMoviesUseCase.execute(any(), any()) } returns ViewState.Success(Unit)

            // when
            val viewModel = MovieListViewModel(getMoviesUseCase, syncMoviesUseCase)

            // then
            getMoviesUseCase.execute().collect {
                MatcherAssert.assertThat(viewModel.movies, emitsValueEventually(movieList))
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `given syncMoviesUseCase succeeds, when syncMovies is called, request state live data will succeed`() =
        runBlockingTest {
            // given
            every { getMoviesUseCase.execute() } returns flowOf(emptyList())
            coEvery { syncMoviesUseCase.execute(any(), any()) } returns ViewState.Success(Unit)

            val viewModel = MovieListViewModel(getMoviesUseCase, syncMoviesUseCase)

            // when
            viewModel.syncNextMoviesPage()

            // then
            MatcherAssert.assertThat(
                viewModel.requestState,
                emitsValueEventually(ViewState.Success(Unit))
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `given syncMoviesUseCase fails, when syncMovies is called, request state live data will fail`() =
        runBlockingTest {
            // given
            every { getMoviesUseCase.execute() } returns flowOf(emptyList())
            val viewModel = MovieListViewModel(getMoviesUseCase, syncMoviesUseCase)
            coEvery { syncMoviesUseCase.execute(any(), any()) } returns ViewState.Error(Constants.INSERTION_FAIL_CODE)

            // when
            viewModel.syncNextMoviesPage()

            // then
            MatcherAssert.assertThat(
                viewModel.requestState,
                emitsValueEventually(ViewState.Error(INSERTION_FAIL_CODE))
            )
        }
}
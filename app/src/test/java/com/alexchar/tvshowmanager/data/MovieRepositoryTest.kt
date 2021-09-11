package com.alexchar.tvshowmanager.data

import com.alexchar.tvshowmanager.MovieListQuery
import com.alexchar.tvshowmanager.data.local.movie.IMovieLocalSource
import com.alexchar.tvshowmanager.data.remote.movie.IMovieRemoteSource
import com.alexchar.tvshowmanager.data.repository.MovieRepository
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.util.Constants
import com.alexchar.tvshowmanager.domain.util.ViewState
import com.apollographql.apollo.api.Response
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class MovieRepositoryTest {

    private val movieRemoteSource = mockk<IMovieRemoteSource>()
    private val movieLocalSource = mockk<IMovieLocalSource>()

    private val repo = MovieRepository(movieRemoteSource, movieLocalSource)

    private val testNode = MovieListQuery.Node(
        __typename = "test",
        title = "test",
        releaseDate = "test",
        seasons = 1.0,
        createdAt = "test"
    )

    @ExperimentalCoroutinesApi
    @Test
    fun `given remote source fails, when sync is called, then return error`() = runBlockingTest {
        // given
        val remoteResult = mockk<Response<MovieListQuery.Data>>()

        every {
            remoteResult.data?.movies?.edges
        } returns null

        coEvery { movieRemoteSource.queryMovieList(10, 0) } returns remoteResult

        // when, then
        Assert.assertEquals(
            repo.syncMovies(10, 0),
            ViewState.Error<String>(Constants.INSERTION_FAIL_CODE)
        )
        coVerify { movieRemoteSource.queryMovieList(10, 0) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given local source fails, when sync is called, then return error`() = runBlockingTest {
        val remoteResult = getRemoteSuccessResult()

        coEvery { movieRemoteSource.queryMovieList(any(), any()) } returns remoteResult

        val localResult = ViewState.Error<Unit>(any())

        coEvery { movieLocalSource.insertAll(any()) } returns localResult

        // when, then
        Assert.assertEquals(repo.syncMovies(10, 0), localResult)
        coVerify { movieRemoteSource.queryMovieList(10, 0) }
        coVerify { movieLocalSource.insertAll(any()) }
    }

    private fun getRemoteSuccessResult(): Response<MovieListQuery.Data> {
        // given
        val remoteResult = mockk<Response<MovieListQuery.Data>>()

        every {
            remoteResult.data?.movies?.edges
        } returns listOf(
            MovieListQuery.Edge(
                node = testNode
            )
        )
        return remoteResult
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given remote and local source succeeds, when sync is called, then success`() =
        runBlockingTest {
            // given
            val remoteResult = getRemoteSuccessResult()

            coEvery { movieRemoteSource.queryMovieList(any(), any()) } returns remoteResult

            val localResult = ViewState.Success(Unit)
            coEvery { movieLocalSource.insertAll(any()) } returns localResult

            // when, then
            Assert.assertEquals(repo.syncMovies(10, 0), ViewState.Success(Unit))
            coVerify { movieRemoteSource.queryMovieList(10, 0) }
            coVerify { movieLocalSource.insertAll(any()) }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `given local source, when getAll is called, then return given movies`() = runBlockingTest {
        // given
        val movieList = getTestMovieList()

        every { movieLocalSource.getMovies() } returns flowOf(movieList)

        // when, then
        repo.getMovies().collect {
            Assert.assertEquals(it, movieList)
        }

        verify { movieLocalSource.getMovies() }
    }

    private fun getTestMovieList(): List<Movie> {
        return listOf(
            Movie(
                id = 1,
                title = "title",
                releaseDate = "releaseDate",
                seasons = 1.0
            )
        )
    }

}
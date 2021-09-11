package com.alexchar.tvshowmanager.data.remote.movie

import com.alexchar.tvshowmanager.CreateMovieMutation
import com.alexchar.tvshowmanager.MovieListQuery
import com.alexchar.tvshowmanager.domain.util.ViewState
import com.alexchar.tvshowmanager.type.CreateMovieFieldsInput
import com.alexchar.tvshowmanager.type.CreateMovieInput
import com.alexchar.tvshowmanager.type.MovieOrder
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import timber.log.Timber
import javax.inject.Inject

class MovieRemoteSource @Inject constructor(
    private val apolloClient: ApolloClient
) : IMovieRemoteSource {
    override suspend fun queryMovieList(first: Int, skip: Int): Response<MovieListQuery.Data> {
        return apolloClient.query(MovieListQuery(first, skip, Input.fromNullable(listOf(MovieOrder.CREATEDAT_DESC)))).await()
    }

    override suspend fun createMovie(movie: MovieListQuery.Node): Response<CreateMovieMutation.Data>?  {
        return try {
            apolloClient.mutate(CreateMovieMutation(
                input = CreateMovieInput(
                    fields = Input.fromNullable(CreateMovieFieldsInput(
                        title = movie.title,
                        releaseDate = Input.fromNullable(movie.releaseDate),
                        seasons = Input.fromNullable(movie.seasons)
                    ))
                )
            )).await()
        } catch (e: ApolloException) {
            Timber.d(e)
            null
        }
    }
}

interface IMovieRemoteSource {
    suspend fun queryMovieList(first: Int, skip: Int): Response<MovieListQuery.Data>
    suspend fun createMovie(movie: MovieListQuery.Node): Response<CreateMovieMutation.Data>?
}
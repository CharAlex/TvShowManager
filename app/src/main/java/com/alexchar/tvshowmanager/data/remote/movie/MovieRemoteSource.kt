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
import javax.inject.Inject

class MovieRemoteSource @Inject constructor(
    private val apolloClient: ApolloClient
) : IMovieRemoteSource {
    override suspend fun queryMovieList(first: Int, skip: Int): Response<MovieListQuery.Data> {
        return apolloClient.query(MovieListQuery(first, skip, Input.fromNullable(listOf(MovieOrder.CREATEDAT_DESC)))).await()
    }
}

interface IMovieRemoteSource {
    suspend fun queryMovieList(first: Int, skip: Int): Response<MovieListQuery.Data>
}
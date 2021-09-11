package com.alexchar.tvshowmanager.data.repository

import com.alexchar.tvshowmanager.data.local.movie.IMovieLocalSource
import com.alexchar.tvshowmanager.data.local.movie.MovieLocalSource
import com.alexchar.tvshowmanager.data.remote.movie.IMovieRemoteSource
import com.alexchar.tvshowmanager.data.remote.movie.MovieRemoteSource
import com.alexchar.tvshowmanager.domain.movie.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @get:Binds
    val MovieRepository.iMovieRepository: IMovieRepository

    @get:Binds
    val MovieRemoteSource.iMovieRemoteSource: IMovieRemoteSource

    @get:Binds
    val MovieLocalSource.iMovieLocalSource: IMovieLocalSource
}
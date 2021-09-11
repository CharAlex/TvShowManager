package com.alexchar.tvshowmanager.domain.movie.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {
    @get:Binds
    val GetMoviesUseCase.iGetMoviesUseCase: IGetMoviesUseCase

    @get:Binds
    val SyncMoviesUseCase.iSyncMoviesUseCase: ISyncMoviesUseCase
}
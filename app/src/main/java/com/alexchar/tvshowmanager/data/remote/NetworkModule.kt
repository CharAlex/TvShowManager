package com.alexchar.tvshowmanager.data.remote

import com.alexchar.tvshowmanager.domain.util.Constants
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(Constants.SERVER_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(requestInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRequestInterceptor() = Interceptor { chain ->
        val url = chain.request()
            .url()

        val request = chain.request()
            .newBuilder()
            .addHeader("X-Parse-Client-Key",
                Constants.CLIENT_KEY
            )
            .addHeader("X-Parse-Application-Id",
                Constants.CLIENT_APPLICATION_ID
            )
            .url(url)
            .build()

        Timber.d("Request: $url")
        return@Interceptor chain.proceed(request)
    }
}
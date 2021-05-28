package com.example.twittercloneappmvp.di.module

import com.example.twittercloneappmvp.BuildConfig
import com.example.twittercloneappmvp.di.annotation.V2OkHttpClient
import com.example.twittercloneappmvp.di.annotation.V1OkHttpClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiConfigModule {

    @Provides
    @Reusable
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    @V1OkHttpClient
    fun provideOkHttpClientForOldApi(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(HEADER_NAME, BuildConfig.OAUTH_HEADER_STRING)
                    .build()
                return@Interceptor chain.proceed(newRequest)
            })
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Reusable
    @V2OkHttpClient
    fun provideOkHttpClientForNewApi(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(HEADER_NAME, BuildConfig.BEARER_TOKEN)
                    .build()
                return@Interceptor chain.proceed(newRequest)
            })
            .addInterceptor(httpLoggingInterceptor)
            .build()

    companion object {
        private const val HEADER_NAME = "Authorization"
    }
}

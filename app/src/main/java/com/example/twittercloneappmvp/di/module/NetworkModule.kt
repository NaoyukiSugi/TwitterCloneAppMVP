package com.example.twittercloneappmvp.di.module

import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.twittercloneappmvp.BuildConfig
import com.example.twittercloneappmvp.di.annotation.OkHttpClientForOldApi
import com.example.twittercloneappmvp.di.annotation.RetrofitForOldApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    @OkHttpClientForOldApi
    fun provideOkHttpClientForOldApi(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(HEADER_NAME, BuildConfig.OAUTH_HEADER_STRING)
                    .build()
                return@Interceptor chain.proceed(newRequest)
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Reusable
    @RetrofitForOldApi
    fun provideRetrofitForNewAPI(@OkHttpClientForOldApi okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Reusable
    fun provideHomeTimelineApi(@RetrofitForOldApi retrofit: Retrofit): HomeTimelineApi =
        retrofit.create(HomeTimelineApi::class.java)

    companion object {
        private const val HEADER_NAME = "Authorization"
        private const val BASE_URL = "https://api.twitter.com/"
    }
}

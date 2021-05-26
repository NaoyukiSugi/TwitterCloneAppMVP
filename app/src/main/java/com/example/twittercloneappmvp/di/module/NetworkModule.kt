package com.example.twittercloneappmvp.di.module

import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.BuildConfig
import com.example.twittercloneappmvp.di.annotation.OkHttpClientForNewApi
import com.example.twittercloneappmvp.di.annotation.OkHttpClientForOldApi
import com.example.twittercloneappmvp.di.annotation.RetrofitForNewApi
import com.example.twittercloneappmvp.di.annotation.RetrofitForOldApi
import dagger.Module
import dagger.Provides
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

    // region API v1.1
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
    @Singleton
    @RetrofitForOldApi
    fun provideRetrofitForOldAPI(@OkHttpClientForOldApi okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideHomeTimelineApi(@RetrofitForOldApi retrofit: Retrofit): HomeTimelineApi =
        retrofit.create(HomeTimelineApi::class.java)
    // endregion

    // region API v2
    @Provides
    @Singleton
    @OkHttpClientForNewApi
    fun provideOkHttpClientForNewApi(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(HEADER_NAME, BuildConfig.BEARER_TOKEN)
                    .build()
                return@Interceptor chain.proceed(newRequest)
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    @RetrofitForNewApi
    fun provideRetrofitForNewAPI(@OkHttpClientForNewApi okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideSearchResultTimelineApi(@RetrofitForNewApi retrofit: Retrofit): SearchResultTimelineApi =
        retrofit.create(SearchResultTimelineApi::class.java)
    // endregion

    companion object {
        private const val HEADER_NAME = "Authorization"
        private const val BASE_URL = "https://api.twitter.com/"
    }
}

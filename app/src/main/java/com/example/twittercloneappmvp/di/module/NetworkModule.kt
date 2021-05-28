package com.example.twittercloneappmvp.di.module

import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.di.annotation.V2OkHttpClient
import com.example.twittercloneappmvp.di.annotation.V1OkHttpClient
import com.example.twittercloneappmvp.di.annotation.V2Retrofit
import com.example.twittercloneappmvp.di.annotation.V1Retrofit
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Reusable
    @V1Retrofit
    fun provideRetrofitForOldAPI(@V1OkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Reusable
    fun provideHomeTimelineApi(@V1Retrofit retrofit: Retrofit): HomeTimelineApi =
        retrofit.create(HomeTimelineApi::class.java)

    @Provides
    @Reusable
    @V2Retrofit
    fun provideRetrofitForNewAPI(@V2OkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Reusable
    fun provideSearchResultTimelineApi(@V2Retrofit retrofit: Retrofit): SearchResultTimelineApi =
        retrofit.create(SearchResultTimelineApi::class.java)

    companion object {
        private const val BASE_URL = "https://api.twitter.com/"
    }
}

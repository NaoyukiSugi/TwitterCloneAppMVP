package com.example.common_api.home_timeline

import retrofit2.Response
import retrofit2.http.GET

interface HomeTimelineApi {
    @GET("1.1/statuses/home_timeline.json")
    suspend fun getHomeTimeline(): Response<List<ResponseTweet>>
}

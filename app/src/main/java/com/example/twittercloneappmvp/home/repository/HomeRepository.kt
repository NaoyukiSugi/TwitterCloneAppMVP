package com.example.twittercloneappmvp.home.repository

import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.home.contract.HomeContract
import com.example.twittercloneappmvp.home.util.Result

class HomeRepository(private val api: HomeTimelineApi) : HomeContract.Repository {

    override suspend fun getHomeTimeline(): Result<List<Tweet>> {
        val response = api.getHomeTimeline()
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.Success(it)
            }
        }
        return Result.Error(response.message())
    }
}

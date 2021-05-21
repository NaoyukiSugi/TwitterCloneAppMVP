package com.example.twittercloneappmvp.feature.home.repository

import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.util.NetworkResult
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeTimelineApi
) : HomeContract.Repository {

    override suspend fun getHomeTimeline(): NetworkResult<List<Tweet>> {
        val response = api.getHomeTimeline()
        if (response.isSuccessful) {
            response.body()?.let {
                return NetworkResult.Success(it)
            }
        }
        return NetworkResult.Error(response.message())
    }
}

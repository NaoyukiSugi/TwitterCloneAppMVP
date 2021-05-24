package com.example.twittercloneappmvp.feature.home.repository

import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.model.Future
import com.example.twittercloneappmvp.util.apiFlow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeTimelineApi
) : HomeContract.Repository {

    override fun getHomeTimeline(): Flow<Future<List<Tweet>>> = apiFlow { api.getHomeTimeline() }
}

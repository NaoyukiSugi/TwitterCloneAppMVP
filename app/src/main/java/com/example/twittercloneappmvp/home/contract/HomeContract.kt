package com.example.twittercloneappmvp.home.contract

import com.example.common_api.home_timeline.Tweet

interface HomeContract {
    interface Repository {
        suspend fun getHomeTimeline(): Result<List<Tweet>>
    }
}

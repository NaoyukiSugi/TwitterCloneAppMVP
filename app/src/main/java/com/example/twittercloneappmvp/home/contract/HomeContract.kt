package com.example.twittercloneappmvp.home.contract

import com.example.common_api.home_timeline.Tweet
import com.example.common_api.home_timeline.User
import com.example.twittercloneappmvp.home.util.Result

interface HomeContract {
    interface Repository {
        suspend fun getHomeTimeline(): Result<List<Tweet>>
    }

    interface ViewHolderViewProxy {
        fun loadImageToView(imageUrl: String)
        fun setName(name: String)
        fun setScreenName(screenName: String)
        fun setDescription(description: String)
        fun setOnClickListener(user: User, listener: IconClickListener?)
    }

    interface IconClickListener {
        fun onIconClick(user: User)
    }
}

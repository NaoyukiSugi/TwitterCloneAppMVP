package com.example.twittercloneappmvp.feature.search_result.contract

import com.example.common_api.search_result.SearchQuery
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.twittercloneappmvp.model.User
import com.example.twittercloneappmvp.util.Result
import kotlinx.coroutines.flow.Flow

interface SearchResultContract {
    interface Repository {
        fun getSearchResultTimeline(
            searchQuery: SearchQuery,
            nextToken: String?
        ): Flow<Result<SearchResultTimelineResponse>>
    }

    interface ViewHolderViewProxy {
        fun loadImageToView(imageUrl: String)
        fun setName(name: String)
        fun setScreenName(screenName: String)
        fun setText(text: String)
        fun setOnClickListener(user: User, listener: IconClickListener?)
    }

    interface IconClickListener {
        fun onIconClick(user: User)
    }
}

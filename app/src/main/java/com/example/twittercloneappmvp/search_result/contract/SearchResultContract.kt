package com.example.twittercloneappmvp.search_result.contract

import com.example.common_api.search_result.SearchQuery
import com.example.common_api.search_result.SearchResultResponse
import com.example.twittercloneappmvp.model.User
import com.example.twittercloneappmvp.util.Result
import kotlinx.coroutines.flow.Flow

interface SearchResultContract {
    interface Repository {
        fun getSearchResultTimeline(
            searchQuery: SearchQuery,
            nextToken: String?
        ): Flow<Result<SearchResultResponse>>
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

package com.example.twittercloneappmvp.feature.search_result.contract

import com.example.common_api.search_result.SearchQuery
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import com.example.twittercloneappmvp.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SearchResultContract {
    interface Repository {
        fun getSearchResultTimeline(
            searchQuery: SearchQuery,
            nextToken: String?
        ): Flow<NetworkResult<SearchResultTimelineResponse>>
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

    interface RefreshListener {
        fun onRefresh()
    }

    interface ViewProxy {
        fun initAdapter()
        fun submitList(tweets: List<Tweet>)
        fun showRecyclerView()
        fun hideRecyclerView()
        fun showErrorView()
        fun hideErrorView()
        fun showEmptyView()
        fun hideEmptyView()
        fun showLoadingView()
        fun hideLoadingView()
        fun setOnIconClickListener(listener: IconClickListener)
        fun setOnRefreshListener(listener: RefreshListener)
    }

    interface Presenter {
        fun getSearchResultTimeline()
    }

}

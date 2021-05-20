package com.example.twittercloneappmvp.feature.home.contract

import android.widget.SearchView
import com.example.common_api.home_timeline.Tweet
import com.example.common_api.home_timeline.User
import com.example.common_api.search_result.SearchQuery
import com.example.twittercloneappmvp.util.Result

interface HomeContract {
    interface Repository {
        suspend fun getHomeTimeline(): Result<List<Tweet>>
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
        fun setOnQueryTextListener(listener: SearchView.OnQueryTextListener)
        fun navigateToSearchResult(searchQuery: String)
    }

    interface RefreshListener {
        fun onRefresh()
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

    interface Presenter {
        fun getHomeTimeline()
    }
}

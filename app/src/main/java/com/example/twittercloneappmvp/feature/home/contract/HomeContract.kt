package com.example.twittercloneappmvp.feature.home.contract

import android.widget.SearchView
import com.example.common_api.home_timeline.Tweet
import com.example.common_api.home_timeline.ResponseUser
import com.example.twittercloneappmvp.model.Future
import kotlinx.coroutines.flow.Flow

interface HomeContract {
    interface Repository {
        fun getHomeTimeline(): Flow<Future<List<Tweet>>>
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
        fun setOnClickListener(user: ResponseUser, listener: IconClickListener?)
    }

    interface IconClickListener {
        fun onIconClick(user: ResponseUser)
    }

    interface Presenter {
        fun getHomeTimeline()
    }
}

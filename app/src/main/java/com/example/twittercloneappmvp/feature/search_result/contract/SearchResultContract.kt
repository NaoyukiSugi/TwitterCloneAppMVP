package com.example.twittercloneappmvp.feature.search_result.contract

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import kotlinx.coroutines.flow.Flow

interface SearchResultContract {
    interface Repository {
        fun getSearchResultTimeline(searchQuery: String): Flow<PagingData<Tweet>>
    }

    interface ViewHolderViewProxy {
        fun loadImageToView(imageUrl: String)
        fun setName(name: String)
        fun setUserName(userName: String)
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
        fun initRecyclerView()
        suspend fun submitData(tweets: PagingData<Tweet>)
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
        fun addLoadStateListener(listener: LoadStateListener)
        fun navigateToProfile(user: User)
        fun refresh()
    }

    interface Presenter {
        fun getSearchResultTimeline(searchQuery: String)
    }

    interface LoadStateListener {
        fun onLoadState(loadState: CombinedLoadStates)
    }
}

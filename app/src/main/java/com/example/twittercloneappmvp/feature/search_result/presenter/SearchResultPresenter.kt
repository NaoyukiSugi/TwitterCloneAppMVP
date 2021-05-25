package com.example.twittercloneappmvp.feature.search_result.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchResultPresenter(
    private val viewProxy: SearchResultContract.ViewProxy,
    private val repository: SearchResultContract.Repository,
    lifecycleOwner: LifecycleOwner
) : SearchResultContract.Presenter, SearchResultContract.IconClickListener,
    SearchResultContract.RefreshListener, LifecycleObserver, CoroutineScope by MainScope() {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifecycleEventOnStart() {
        viewProxy.run {
            initAdapter()
            setOnIconClickListener(this@SearchResultPresenter)
            setOnRefreshListener(this@SearchResultPresenter)
            addLoadStateListener()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleEventOnDestroy() = cancel()

    override fun getSearchResultTimeline(searchQuery: String) {
        launch {
            repository.getSearchResultTimeline(searchQuery).collect {
                viewProxy.submitData(it)
            }
        }
    }

    override fun onIconClick(user: User) {
        TODO("Not yet implemented")
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }
}

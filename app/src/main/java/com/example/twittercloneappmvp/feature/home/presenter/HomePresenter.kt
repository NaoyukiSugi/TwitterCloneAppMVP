package com.example.twittercloneappmvp.feature.home.presenter

import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.model.Future
import com.example.twittercloneappmvp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val viewProxy: HomeContract.ViewProxy,
    private val repository: HomeContract.Repository,
    lifecycleOwner: LifecycleOwner
) : HomeContract.Presenter,
    HomeContract.IconClickListener,
    HomeContract.RefreshListener,
    SearchView.OnQueryTextListener,
    LifecycleObserver, CoroutineScope by MainScope() {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifecycleEventOnStart() {
        viewProxy.run {
            initAdapter()
            initRecyclerView()
            setOnIconClickListener(this@HomePresenter)
            setOnRefreshListener(this@HomePresenter)
            setOnQueryTextListener(this@HomePresenter)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleEventOnDestroy() = cancel()

    override fun getHomeTimeline() {
        launch {
            repository.getHomeTimeline()
                .collect {
                    when (it) {
                        is Future.Proceeding -> {
                            viewProxy.run {
                                showLoadingView()
                                hideErrorView()
                                hideEmptyView()
                                hideRecyclerView()
                            }
                        }
                        is Future.Success -> {
                            viewProxy.hideLoadingView()
                            it.transform { tweets ->
                                if (tweets.isNotEmpty()) {
                                    viewProxy.run {
                                        hideErrorView()
                                        hideEmptyView()
                                        showRecyclerView()
                                        submitList(tweets)
                                    }
                                } else {
                                    viewProxy.run {
                                        hideRecyclerView()
                                        hideErrorView()
                                        showEmptyView()
                                    }
                                }
                            }
                        }
                        is Future.Error -> {
                            viewProxy.run {
                                hideLoadingView()
                                hideRecyclerView()
                                hideEmptyView()
                                showErrorView()
                            }
                        }
                    }
                }
        }
    }

    override fun onRefresh() {
        viewProxy.initAdapter()
        getHomeTimeline()
    }

    override fun onIconClick(user: User) {
        viewProxy.navigateToProfile(user)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewProxy.navigateToSearchResult(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // do nothing
        return false
    }
}

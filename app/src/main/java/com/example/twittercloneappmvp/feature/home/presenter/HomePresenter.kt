package com.example.twittercloneappmvp.feature.home.presenter

import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.common_api.home_timeline.ResponseUser
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.util.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
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
            setOnIconClickListener(this@HomePresenter)
            setOnRefreshListener(this@HomePresenter)
            setOnQueryTextListener(this@HomePresenter)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleEventOnDestroy() = cancel()

    override fun getHomeTimeline() {
        launch {
            viewProxy.showLoadingView()
            when (val result = repository.getHomeTimeline()) {
                is NetworkResult.Success -> {
                    if (result.data != null && result.data.isNotEmpty()) {
                        viewProxy.run {
                            hideErrorView()
                            hideEmptyView()
                            showRecyclerView()
                            submitList(result.data)
                        }
                    } else {
                        viewProxy.run {
                            hideRecyclerView()
                            hideErrorView()
                            showEmptyView()
                        }
                    }
                }
                is NetworkResult.Error -> {
                    viewProxy.run {
                        hideRecyclerView()
                        hideEmptyView()
                        showErrorView()
                    }
                }
            }
            viewProxy.hideLoadingView()
        }
    }

    override fun onRefresh() {
        viewProxy.initAdapter()
        getHomeTimeline()
    }

    override fun onIconClick(user: ResponseUser) {
        TODO("Not yet implemented")
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

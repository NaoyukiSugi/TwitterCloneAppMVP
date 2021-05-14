package com.example.twittercloneappmvp.home.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.common_api.home_timeline.User
import com.example.twittercloneappmvp.home.contract.HomeContract
import com.example.twittercloneappmvp.home.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HomePresenter(
    private val viewProxy: HomeContract.ViewProxy,
    private val repository: HomeContract.Repository,
    private val lifecycleOwner: LifecycleOwner
) : HomeContract.Presenter,
    HomeContract.IconClickListener,
    HomeContract.RefreshListener,
    LifecycleObserver, CoroutineScope by MainScope() {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onLifecycleEventOnCreate() {
        viewProxy.run {
            initAdapter()
            setOnIconClickListener(this@HomePresenter)
            setOnRefreshListener(this@HomePresenter)
        }
        getHomeTimeline()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onLifecycleEventOnDestroy() = cancel()

    override fun getHomeTimeline() {
        launch {
            when (val result = repository.getHomeTimeline()) {
                is Result.Success -> {
                    if (result.data != null) {
                        viewProxy.run {
                            showRecyclerView()
                            hideErrorView()
                            hideEmptyView()
                            hideLoadingView()
                            submitList(result.data)
                        }
                    } else {
                        viewProxy.run {
                            showEmptyView()
                            hideRecyclerView()
                            hideErrorView()
                            hideLoadingView()
                        }
                    }
                }
                is Result.Error -> {
                    viewProxy.run {
                        showErrorView()
                        hideRecyclerView()
                        hideEmptyView()
                        hideLoadingView()
                    }
                }
                is Result.Loading -> {
                    viewProxy.run {
                        showLoadingView()
                    }
                }
            }
        }
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }

    override fun onIconClick(user: User) {
        TODO("Not yet implemented")
    }
}

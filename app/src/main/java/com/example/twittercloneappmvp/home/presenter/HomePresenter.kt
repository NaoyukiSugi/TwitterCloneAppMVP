package com.example.twittercloneappmvp.home.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.common_api.home_timeline.User
import com.example.twittercloneappmvp.home.contract.HomeContract
import com.example.twittercloneappmvp.home.util.Result
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class HomePresenter @Inject constructor(
    private val viewProxy: HomeContract.ViewProxy,
    private val repository: HomeContract.Repository,
    @Named("LifecycleOwner") lifecycleOwner: LifecycleOwner
) : HomeContract.Presenter,
    HomeContract.IconClickListener,
    HomeContract.RefreshListener,
    LifecycleObserver, CoroutineScope by MainScope() {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onLifecycleEventOnCreate() {
        viewProxy.run {
            initAdapter()
            setOnIconClickListener(this@HomePresenter)
            setOnRefreshListener(this@HomePresenter)
        }
        getHomeTimeline()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleEventOnDestroy() = cancel()

    override fun getHomeTimeline() {
        launch {
            viewProxy.showLoadingView()
            when (val result = repository.getHomeTimeline()) {
                is Result.Success -> {
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
                is Result.Error -> {
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

    override fun onIconClick(user: User) {
        TODO("Not yet implemented")
    }
}

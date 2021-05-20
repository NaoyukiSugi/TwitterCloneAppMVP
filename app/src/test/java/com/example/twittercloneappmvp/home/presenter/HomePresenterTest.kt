package com.example.twittercloneappmvp.home.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.home.contract.HomeContract
import com.example.twittercloneappmvp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertFalse

internal class HomePresenterTest {

    private lateinit var presenter: HomePresenter
    private val viewProxy: HomeContract.ViewProxy = mock()
    private val repository: HomeContract.Repository = mock()
    private val lifecycle: Lifecycle = mock()
    private val lifecycleOwner: LifecycleOwner = mock {
        on { lifecycle } doReturn (lifecycle)
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        presenter = spy(HomePresenter(viewProxy, repository, lifecycleOwner))
    }

    // region onLifecycleEventOnStart
    @Test
    fun `onLifecycleEventOnStart should call initAdapter`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).initAdapter()
    }

    @Test
    fun `onLifecycleEventOnStart should call setOnIconClickListener`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).setOnIconClickListener(presenter)
    }

    @Test
    fun `onLifecycleEventOnStart should call setOnRefreshListener`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).setOnRefreshListener(presenter)
    }
    // endregion

    @Test
    fun `onLifecycleEventOnDestroy should call cancel`() {
        presenter.onLifecycleEventOnDestroy()

        assertFalse(presenter.isActive)
    }

    // region getHomeTimeline
    @Test
    fun `getHomeTimeline should call showLoadingView`() {
        presenter.getHomeTimeline()

        verify(viewProxy).showLoadingView()
    }

    @Test
    fun `getHomeTimeline should only show recyclerView and submit list when result data is not empty`() {
        runBlockingTest {
            val tweetList =
                listOf(Tweet(id = 0L, createdAt = "createdAt", text = "text", user = mock()))
            val mockResult = Result.Success(tweetList)
            doReturn(mockResult).whenever(repository).getHomeTimeline()

            presenter.getHomeTimeline()

            verify(viewProxy).hideErrorView()
            verify(viewProxy).hideEmptyView()
            verify(viewProxy).showRecyclerView()
            verify(viewProxy).submitList(tweetList)
        }
    }

    @Test
    fun `getHomeTimeline should only show emptyView when result data is null`() {
        runBlockingTest {
            val mockResult = Result.Success(null)
            doReturn(mockResult).whenever(repository).getHomeTimeline()

            presenter.getHomeTimeline()

            verify(viewProxy).hideRecyclerView()
            verify(viewProxy).hideErrorView()
            verify(viewProxy).showEmptyView()
        }
    }

    @Test
    fun `getHomeTimeline should only show emptyView when result data is empty`() {
        runBlockingTest {
            val mockResult = Result.Success(emptyList<Tweet>())
            doReturn(mockResult).whenever(repository).getHomeTimeline()

            presenter.getHomeTimeline()

            verify(viewProxy).hideRecyclerView()
            verify(viewProxy).hideErrorView()
            verify(viewProxy).showEmptyView()
        }
    }

    @Test
    fun `getHomeTimeline should only show errorView when result is Error`() {
        runBlockingTest {
            val mockResult = Result.Error(message = "message", data = null)
            doReturn(mockResult).whenever(repository).getHomeTimeline()

            presenter.getHomeTimeline()

            verify(viewProxy).hideRecyclerView()
            verify(viewProxy).hideEmptyView()
            verify(viewProxy).showErrorView()
        }
    }

    @Test
    fun `getHomeTimeline should call hideLoadingView`() {
        presenter.getHomeTimeline()

        verify(viewProxy).hideLoadingView()
    }
    // endregion

    // region onRefresh
    @Test
    fun `onRefresh should call initAdapter`() {
        presenter.onRefresh()

        verify(viewProxy).initAdapter()
    }

    @Test
    fun `onRefresh should call getHomeTimeline`() {
        presenter.onRefresh()

        verify(presenter).getHomeTimeline()
    }
    // endregion
}

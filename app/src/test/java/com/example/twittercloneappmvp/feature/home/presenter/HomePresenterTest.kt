package com.example.twittercloneappmvp.feature.home.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.model.Future
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
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

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // region onLifecycleEventOnStart
    @Test
    fun `onLifecycleEventOnStart should call initAdapter`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).initAdapter()
    }

    @Test
    fun `onLifecycleEventOnStart should call initRecyclerView`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).initRecyclerView()
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
    fun `getHomeTimeline should show only recyclerView and submit list when result data is not empty`() {
        runBlockingTest {
            val tweetList =
                listOf(Tweet(id = 0L, createdAt = "createdAt", text = "text", user = mock()))
            doReturn(flowOf(Future.Success(tweetList))).whenever(repository).getHomeTimeline()

            presenter.getHomeTimeline()

            verify(viewProxy).hideLoadingView()
            verify(viewProxy).hideErrorView()
            verify(viewProxy).hideEmptyView()
            verify(viewProxy).showRecyclerView()
            verify(viewProxy).submitList(tweetList)
        }
    }

    @Test
    fun `getHomeTimeline should show only emptyView when result data is empty`() {
        runBlockingTest {
            val tweetList = emptyList<Tweet>()
            doReturn(flowOf(Future.Success(tweetList))).whenever(repository).getHomeTimeline()

            presenter.getHomeTimeline()

            verify(viewProxy).hideLoadingView()
            verify(viewProxy).hideErrorView()
            verify(viewProxy).hideRecyclerView()
            verify(viewProxy).showEmptyView()
        }
    }

    @Test
    fun `getHomeTimeline should show only errorView when result is Error`() {
        runBlockingTest {
            doReturn(flowOf(Future.Error(mock()))).whenever(repository).getHomeTimeline()

            presenter.getHomeTimeline()

            verify(viewProxy).hideLoadingView()
            verify(viewProxy).hideRecyclerView()
            verify(viewProxy).hideEmptyView()
            verify(viewProxy).showErrorView()
        }
    }

    @Test
    fun `getHomeTimeline should show only showLoadingView when result is Proceding`() {
        runBlockingTest {
            doReturn(flowOf(Future.Proceeding)).whenever(repository).getHomeTimeline()

            presenter.getHomeTimeline()

            verify(viewProxy).showLoadingView()
            verify(viewProxy).hideErrorView()
            verify(viewProxy).hideEmptyView()
            verify(viewProxy).hideRecyclerView()
        }
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

    @Test
    fun `onIconClick should call navigateToProfile`() {
        val user: User = mock()

        presenter.onIconClick(user)

        verify(viewProxy).navigateToProfile(user)
    }

}

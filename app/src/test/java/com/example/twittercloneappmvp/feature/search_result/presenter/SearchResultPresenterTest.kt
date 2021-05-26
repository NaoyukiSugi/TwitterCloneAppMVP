package com.example.twittercloneappmvp.feature.search_result.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.model.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertFalse

internal class SearchResultPresenterTest {

    private lateinit var presenter: SearchResultPresenter
    private val viewProxy: SearchResultContract.ViewProxy = mock()
    private val repository: SearchResultContract.Repository = mock()
    private val lifecycle: Lifecycle = mock()
    private val lifecycleOwner: LifecycleOwner = mock { on { lifecycle } doReturn lifecycle }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        presenter = spy(SearchResultPresenter(viewProxy, repository, lifecycleOwner))
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

    @Test
    fun `getSearchResultTimeline should submit pagingData passed from repository`() {
        runBlocking {
            val searchQuery = "searchQuery"
            val pagingDataFLow: Flow<PagingData<Tweet>> = mock()
            doReturn(pagingDataFLow).whenever(repository).getSearchResultTimeline(searchQuery)

            presenter.getSearchResultTimeline(searchQuery)

            verify(repository).getSearchResultTimeline(searchQuery)
            pagingDataFLow.collect {
                verify(viewProxy).submitData(it)
            }
        }
    }

    // region onLoadState
    @Test
    fun `onLoadState should only show recyclerView when LoadState is NotLoading`() {
        val loadState: CombinedLoadStates = mock {
            on { refresh } doReturn LoadState.NotLoading(false)
        }

        presenter.onLoadState(loadState)

        verify(viewProxy).showRecyclerView()
        verify(viewProxy).hideEmptyView()
        verify(viewProxy).hideErrorView()
        verify(viewProxy).hideLoadingView()
    }

    @Test
    fun `onLoadState should only show loadingView when LoadState is Loading`() {
        val loadState: CombinedLoadStates = mock {
            on { refresh } doReturn LoadState.Loading
        }

        presenter.onLoadState(loadState)

        verify(viewProxy).showLoadingView()
        verify(viewProxy).hideRecyclerView()
        verify(viewProxy).hideEmptyView()
        verify(viewProxy).hideErrorView()
    }

    @Test
    fun `onLoadState should only show errorView when LoadState is Error`() {
        val loadState: CombinedLoadStates = mock {
            on { refresh } doReturn LoadState.Error(mock())
        }

        presenter.onLoadState(loadState)

        verify(viewProxy).showErrorView()
        verify(viewProxy).hideLoadingView()
        verify(viewProxy).hideRecyclerView()
        verify(viewProxy).hideEmptyView()
    }
    // endregion
}

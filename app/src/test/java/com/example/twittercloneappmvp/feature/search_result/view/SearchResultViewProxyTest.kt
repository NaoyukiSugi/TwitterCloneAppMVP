package com.example.twittercloneappmvp.feature.search_result.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.model.Tweet
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

internal class SearchResultViewProxyTest {

    private lateinit var viewProxy: SearchResultViewProxy
    private val recyclerView: RecyclerView = mock()
    private val errorView: View = mock()
    private val emptyView: View = mock()
    private val progressBar: ContentLoadingProgressBar = mock()
    private val refreshButton: Button = mock()
    private val view: View = mock {
        on { findViewById<RecyclerView>(R.id.recycler_view) } doReturn recyclerView
        on { findViewById<View>(R.id.error_view) } doReturn errorView
        on { findViewById<View>(R.id.empty_view) } doReturn emptyView
        on { findViewById<ContentLoadingProgressBar>(R.id.progress_bar) } doReturn progressBar
        on { findViewById<Button>(R.id.refresh_button) } doReturn refreshButton
    }
    private val drawable: Drawable = mock()
    private val context: Context = mock {
        on { getDrawable(R.drawable.divider) } doReturn drawable
    }
    private val fragment: Fragment = mock {
        on { view } doReturn view
        on { context } doReturn context
    }
    private val adapter: SearchResultAdapter = mock()

    @BeforeEach
    fun setUp() {
        viewProxy = spy(SearchResultViewProxy(fragment))
        doReturn(adapter).whenever(viewProxy).createSearchResultAdapter()
    }

    @Test
    fun `initAdapter should set adapter into recyclerview`() {
        viewProxy.initAdapter()

        verify(recyclerView).adapter = adapter
    }

    @Test
    fun `initRecyclerView should call addItemDecoration`() {
        viewProxy.initRecyclerView()
        val itemDecoration: RecyclerView.ItemDecoration = mock()
        doReturn(itemDecoration).whenever(viewProxy).createItemDecoration()

        verify(recyclerView).addItemDecoration(itemDecoration)
    }

    @Test
    fun `submitList should call submitList of adapter`() {
        runBlocking {
            val tweets: PagingData<Tweet> = mock()
            viewProxy.searchResultAdapter = adapter

            viewProxy.submitData(tweets)

            verify(adapter).submitData(tweets)
        }
    }

    @Test
    fun `showRecyclerView should show recyclerView`() {
        viewProxy.showRecyclerView()

        verify(recyclerView).isVisible = true
    }

    @Test
    fun `hideRecyclerView should hide recyclerView`() {
        viewProxy.hideRecyclerView()

        verify(recyclerView).isVisible = false
    }

    @Test
    fun `showErrorView should show errorView`() {
        viewProxy.showErrorView()

        verify(errorView).isVisible = true
    }

    @Test
    fun `hideErrorView should hide errorView`() {
        viewProxy.hideErrorView()

        verify(errorView).isVisible = false
    }

    @Test
    fun `showEmptyView should show emptyView`() {
        viewProxy.showEmptyView()

        verify(emptyView).isVisible = true
    }

    @Test
    fun `hideEmptyView should hide emptyView`() {
        viewProxy.hideEmptyView()

        verify(emptyView).isVisible = false
    }

    @Test
    fun `showLoadingView should show progressBar`() {
        viewProxy.showLoadingView()

        verify(progressBar).isVisible = true
    }

    @Test
    fun `hideLoadingView should hide progressBar`() {
        viewProxy.hideLoadingView()

        verify(progressBar).isVisible = false
    }

    @Test
    fun `setOnIconClickListener should set listener into adapter`() {
        val listener: SearchResultContract.IconClickListener = mock()
        viewProxy.searchResultAdapter = adapter

        viewProxy.setOnIconClickListener(listener)

        verify(adapter).listener = listener
    }

    @Test
    fun `setOnRefreshListener should set listener into refreshButton`() {
        val refreshListener: SearchResultContract.RefreshListener = mock()

        viewProxy.setOnRefreshListener(refreshListener)

        val listener = argumentCaptor<View.OnClickListener>() {
            verify(refreshButton).setOnClickListener(capture())
        }.firstValue
        listener.onClick(refreshButton)

        verify(refreshListener).onRefresh()
    }

    @Test
    fun `addLoadStateListener should set listener into searchResultAdapter`() {
        val loadStateListener: SearchResultContract.LoadStateListener = mock()
        viewProxy.searchResultAdapter = adapter

        viewProxy.addLoadStateListener(loadStateListener)

        argumentCaptor<(CombinedLoadStates) -> Unit> {
            verify(adapter).addLoadStateListener(capture())
        }.firstValue
    }
}

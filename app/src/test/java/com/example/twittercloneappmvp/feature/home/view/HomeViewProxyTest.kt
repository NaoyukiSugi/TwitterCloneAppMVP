package com.example.twittercloneappmvp.feature.home.view

import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

internal class HomeViewProxyTest {

    private lateinit var viewProxy: HomeViewProxy
    private val recyclerView: RecyclerView = mock()
    private val errorView: View = mock()
    private val emptyView: View = mock()
    private val progressBar: ContentLoadingProgressBar = mock()
    private val refreshButton: Button = mock()
    private val searchView: SearchView = mock()
    private val view: View = mock {
        on { findViewById<RecyclerView>(R.id.recycler_view) } doReturn recyclerView
        on { findViewById<View>(R.id.error_view) } doReturn errorView
        on { findViewById<View>(R.id.empty_view) } doReturn emptyView
        on { findViewById<ContentLoadingProgressBar>(R.id.progress_bar) } doReturn progressBar
        on { findViewById<Button>(R.id.refresh_button) } doReturn refreshButton
        on { findViewById<SearchView>(R.id.search_view) } doReturn searchView
    }
    private val fragment: Fragment = mock {
        on { view } doReturn view
    }
    private val adapter: HomeAdapter = mock()
    private val navController = TestNav

    @BeforeEach
    fun setUp() {
        viewProxy = spy(HomeViewProxy(fragment))
        doReturn(adapter).whenever(viewProxy).createHomeAdapter()
    }

    @Test
    fun `initAdapter should set adapter into recyclerview`() {
        viewProxy.initAdapter()

        verify(recyclerView).adapter = adapter
    }

    @Test
    fun `submitList should call submitList of adapter`() {
        val tweets: List<Tweet> = mock()
        viewProxy.homeAdapter = adapter

        viewProxy.submitList(tweets)

        verify(adapter).submitList(tweets)
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
        val listener: HomeContract.IconClickListener = mock()
        viewProxy.homeAdapter = adapter

        viewProxy.setOnIconClickListener(listener)

        verify(adapter).listener = listener
    }

    @Test
    fun `setOnRefreshListener should set listener into refreshButton`() {
        val refreshListener: HomeContract.RefreshListener = mock()

        viewProxy.setOnRefreshListener(refreshListener)

        val listener = argumentCaptor<View.OnClickListener>() {
            verify(refreshButton).setOnClickListener(capture())
        }.firstValue
        listener.onClick(refreshButton)

        verify(refreshListener).onRefresh()
    }

    @Test
    fun `setOnQueryTextListener should set listener into searchView`() {
        val listener: SearchView.OnQueryTextListener = mock()

        viewProxy.setOnQueryTextListener(listener)

        verify(searchView).setOnQueryTextListener(listener)
    }
}

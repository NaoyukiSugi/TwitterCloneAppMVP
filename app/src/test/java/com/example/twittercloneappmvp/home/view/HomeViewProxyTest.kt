package com.example.twittercloneappmvp.home.view

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.home.contract.HomeContract
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class HomeViewProxyTest {

    private lateinit var viewProxy: HomeViewProxy
    private val recyclerView: RecyclerView = mock()
    private val errorView: View = mock()
    private val emptyView: View = mock()
    private val progressBar: ContentLoadingProgressBar = mock()
    private val refreshButton: Button = mock()
    private val activity: AppCompatActivity = mock {
        on { findViewById<RecyclerView>(R.id.recycler_view) } doReturn recyclerView
        on { findViewById<View>(R.id.error_view) } doReturn errorView
        on { findViewById<View>(R.id.empty_view) } doReturn emptyView
        on { findViewById<ContentLoadingProgressBar>(R.id.progress_bar) } doReturn progressBar
        on { findViewById<Button>(R.id.refresh_button) } doReturn refreshButton
    }
    private val adapter: HomeAdapter = mock()

    @BeforeEach
    fun setUp() {
        viewProxy = HomeViewProxy(activity, adapter)
    }

    @Test
    fun `initAdapter should set adapter into recyclerview`() {
        viewProxy.initAdapter()

        verify(recyclerView).adapter = adapter
    }

    @Test
    fun `submitList should call submitList of adapter`() {
        val tweets: List<Tweet> = mock()

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
}
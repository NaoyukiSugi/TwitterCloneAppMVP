package com.example.twittercloneappmvp.feature.search_result.view

import android.view.View
import android.widget.Button
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.feature.search_result.fragment.SearchResultFragmentDirections
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import com.example.twittercloneappmvp.util.navigateSafe
import javax.inject.Inject

class SearchResultViewProxy @Inject constructor(private val fragment: Fragment) :
    SearchResultContract.ViewProxy {

    @VisibleForTesting
    internal lateinit var searchResultAdapter: SearchResultAdapter

    private val recyclerView: RecyclerView?
        get() = fragment.view?.findViewById(R.id.recycler_view)

    private val errorView: View?
        get() = fragment.view?.findViewById(R.id.error_view)

    private val emptyView: View?
        get() = fragment.view?.findViewById(R.id.empty_view)

    private val refreshButton: Button?
        get() = fragment.view?.findViewById(R.id.refresh_button)

    private val swipeRefreshLayout: SwipeRefreshLayout?
        get() = fragment.view?.findViewById(R.id.swipe_refresh_layout)

    private val progressBar: ContentLoadingProgressBar?
        get() = fragment.view?.findViewById(R.id.progress_bar)

    override fun initAdapter() {
        createSearchResultAdapter().run {
            searchResultAdapter = this
            recyclerView?.adapter = this
        }
    }

    override fun initRecyclerView() {
        recyclerView?.run { addItemDecoration(createItemDecoration()) }
    }

    override suspend fun submitData(tweets: PagingData<Tweet>) {
        searchResultAdapter.submitData(tweets)
    }

    override fun showRecyclerView() {
        recyclerView?.isVisible = true
    }

    override fun hideRecyclerView() {
        recyclerView?.isVisible = false
    }

    override fun showErrorView() {
        errorView?.isVisible = true
    }

    override fun hideErrorView() {
        errorView?.isVisible = false
    }

    override fun showEmptyView() {
        emptyView?.isVisible = true
    }

    override fun hideEmptyView() {
        emptyView?.isVisible = false
    }

    override fun showLoadingView() {
        progressBar?.isVisible = true
    }

    override fun hideLoadingView() {
        progressBar?.isVisible = false
    }

    override fun setOnIconClickListener(listener: SearchResultContract.IconClickListener) {
        searchResultAdapter.listener = listener
    }

    override fun setOnRefreshListener(listener: SearchResultContract.RefreshListener) {
        refreshButton?.setOnClickListener { listener.onRefresh() }
        swipeRefreshLayout?.run {
            setOnRefreshListener {
                isRefreshing = false
                listener.onRefresh()
            }
        }
    }

    override fun addLoadStateListener(listener: SearchResultContract.LoadStateListener) {
        searchResultAdapter.addLoadStateListener { listener.onLoadState(it) }
    }

    override fun navigateToProfile(user: User) {
        fragment
            .findNavController()
            .navigateSafe(
                SearchResultFragmentDirections.actionSearchResultFragmentToProfileFragment(user)
            )
    }

    override fun refresh() {
        searchResultAdapter.refresh()
    }

    @VisibleForTesting
    internal fun createSearchResultAdapter() = SearchResultAdapter()

    @VisibleForTesting
    internal fun createItemDecoration() =
        DividerItemDecoration(fragment.context, DividerItemDecoration.VERTICAL)
            .apply {
                fragment.context?.getDrawable(R.drawable.divider)?.let { setDrawable(it) }
            }
}

package com.example.twittercloneappmvp.feature.home.view

import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.feature.home.fragment.HomeFragmentDirections
import com.example.twittercloneappmvp.util.navigateSafe
import javax.inject.Inject

class HomeViewProxy @Inject constructor(
    private val fragment: Fragment
) : HomeContract.ViewProxy {

    @VisibleForTesting
    internal lateinit var homeAdapter: HomeAdapter

    private val recyclerView: RecyclerView?
        get() = fragment.view?.findViewById(R.id.recycler_view)

    private val errorView: View?
        get() = fragment.view?.findViewById(R.id.error_view)

    private val emptyView: View?
        get() = fragment.view?.findViewById(R.id.empty_view)

    private val refreshButton: Button?
        get() = fragment.view?.findViewById(R.id.refresh_button)

    private val progressBar: ContentLoadingProgressBar?
        get() = fragment.view?.findViewById(R.id.progress_bar)

    private val searchView: SearchView?
        get() = fragment.view?.findViewById(R.id.search_view)

    override fun initAdapter() {
        createHomeAdapter().run {
            homeAdapter = this
            recyclerView?.adapter = this
        }
    }

    override fun submitList(tweets: List<Tweet>) {
        homeAdapter.submitList(tweets)
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

    override fun setOnIconClickListener(listener: HomeContract.IconClickListener) {
        homeAdapter.listener = listener
    }

    override fun setOnRefreshListener(listener: HomeContract.RefreshListener) {
        refreshButton?.setOnClickListener { listener.onRefresh() }
    }

    override fun setOnQueryTextListener(listener: SearchView.OnQueryTextListener) {
        searchView?.setOnQueryTextListener(listener)
    }

    override fun navigateToSearchResult(searchQuery: String) {
        fragment
            .findNavController()
            .navigateSafe(
                HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(searchQuery)
            )
    }

    @VisibleForTesting
    internal fun createHomeAdapter() = HomeAdapter()
}

package com.example.twittercloneappmvp.feature.home.view

import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.feature.home.fragment.HomeFragmentDirections
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
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

    private val swipeRefreshLayout: SwipeRefreshLayout?
        get() = fragment.view?.findViewById(R.id.swipe_refresh_layout)

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

    override fun initRecyclerView() {
        recyclerView?.run { addItemDecoration(createItemDecoration()) }
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
        swipeRefreshLayout?.run {
            setOnRefreshListener {
                isRefreshing = false
                listener.onRefresh()
            }
        }
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

    override fun navigateToProfile(user: User) {
        fragment
            .findNavController()
            .navigateSafe(HomeFragmentDirections.actionHomeFragmentToProfileFragment(user))
    }

    @VisibleForTesting
    internal fun createHomeAdapter() = HomeAdapter()

    @VisibleForTesting
    internal fun createItemDecoration() =
        DividerItemDecoration(fragment.context, DividerItemDecoration.VERTICAL)
            .apply {
                fragment.context?.getDrawable(R.drawable.divider)?.let { setDrawable(it) }
            }
}

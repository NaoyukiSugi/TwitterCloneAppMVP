package com.example.twittercloneappmvp.feature.search_result.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.model.Tweet

class SearchResultAdapter : ListAdapter<Tweet, SearchResultViewHolder>(DIFF_CALLBACK) {

    var listener: SearchResultContract.IconClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder =
        createSearchResultViewHolder(parent)

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        getTweet(position)?.run { holder.bind(this, listener) }
    }
    
    @VisibleForTesting
    internal fun createSearchResultViewHolder(parent: ViewGroup): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_tweet, parent, false)
        return SearchResultViewHolder(view)
    }

    @VisibleForTesting
    internal fun getTweet(position: Int) = getItem(position)

    companion object {
        @VisibleForTesting
        internal val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tweet>() {

            override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
                return oldItem == newItem
            }
        }
    }
}

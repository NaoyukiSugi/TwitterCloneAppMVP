package com.example.twittercloneappmvp.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.home.contract.HomeContract

class HomeAdapter : ListAdapter<Tweet, HomeViewHolder>(DIFF_CALLBACK) {

    var listener: HomeContract.IconClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    @VisibleForTesting
    internal fun createHomeViewHolder(parent: ViewGroup): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_tweet, parent, false)
        return HomeViewHolder(view, listener)
    }

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

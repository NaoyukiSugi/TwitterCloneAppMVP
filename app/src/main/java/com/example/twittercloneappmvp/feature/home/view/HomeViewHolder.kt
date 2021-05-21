package com.example.twittercloneappmvp.feature.home.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.common_api.home_timeline.Tweet
import com.example.common_api.home_timeline.ResponseUser
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.home.contract.HomeContract

class HomeViewHolder(
    private val view: View,
    private val viewProxy: HomeContract.ViewHolderViewProxy = ViewHolderViewProxy(view)
) : RecyclerView.ViewHolder(view) {

    fun bind(tweet: Tweet, listener: HomeContract.IconClickListener?) {
        viewProxy.run {
            loadImageToView(tweet.user.profileImageUrlHttps)
            setName(tweet.user.name)
            setScreenName(tweet.user.screenName)
            setText(tweet.text)
            setOnClickListener(tweet.user, listener)
        }
    }

    internal class ViewHolderViewProxy(
        private val view: View,
        private val requestManager: RequestManager = Glide.with(view)
    ) : HomeContract.ViewHolderViewProxy {

        private val iconIv: ImageView
            get() = view.findViewById(R.id.user_icon)

        private val nameTv: TextView
            get() = view.findViewById(R.id.user_name)

        private val screenNameTv: TextView
            get() = view.findViewById(R.id.user_screen_name)

        private val textTv: TextView
            get() = view.findViewById(R.id.tweet_text)

        override fun loadImageToView(imageUrl: String) {
            requestManager.load(imageUrl).into(iconIv)
        }

        override fun setName(name: String) {
            nameTv.text = name
        }

        override fun setScreenName(screenName: String) {
            screenNameTv.text = screenName
        }

        override fun setText(text: String) {
            textTv.text = text
        }

        override fun setOnClickListener(user: ResponseUser, listener: HomeContract.IconClickListener?) {
            iconIv.setOnClickListener { listener?.onIconClick(user) }
        }
    }
}

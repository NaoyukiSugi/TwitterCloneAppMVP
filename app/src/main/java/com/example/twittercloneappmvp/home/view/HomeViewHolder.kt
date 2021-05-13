package com.example.twittercloneappmvp.home.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.common_api.home_timeline.Tweet
import com.example.common_api.home_timeline.User
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.home.contract.HomeContract

class HomeViewHolder(
    view: View,
    private val viewProxy: HomeContract.ViewHolderViewProxy
) : RecyclerView.ViewHolder(view) {

    fun bind(tweet: Tweet, listener: HomeContract.IconClickListener?) {
        viewProxy.run {
            loadImageToView(tweet.user.profileImageUrlHttps)
            setName(tweet.user.name)
            setScreenName(tweet.user.screenName)
            setDescription(tweet.text)
        }
    }

    internal class ViewHolderViewProxy(
        private val view: View,
        private val requestManager: RequestManager = Glide.with(view)
    ) : HomeContract.ViewHolderViewProxy {

        private val iconIv: ImageView
            get() = view.findViewById(R.id.home_user_icon)

        private val nameTv: TextView
            get() = view.findViewById(R.id.home_user_name)

        private val screenNameTv: TextView
            get() = view.findViewById(R.id.home_user_screen_name)

        private val descriptionTv: TextView
            get() = view.findViewById(R.id.home_user_description)

        override fun loadImageToView(imageUrl: String) {
            requestManager.load(imageUrl).into(iconIv)
        }

        override fun setName(name: String) {
            nameTv.text = name
        }

        override fun setScreenName(screenName: String) {
            screenNameTv.text = screenName
        }

        override fun setDescription(description: String) {
            descriptionTv.text = description
        }

        override fun setOnClickListener(user: User, listener: HomeContract.IconClickListener?) {
            iconIv.setOnClickListener { listener?.onIconClick(user) }
        }
    }
}

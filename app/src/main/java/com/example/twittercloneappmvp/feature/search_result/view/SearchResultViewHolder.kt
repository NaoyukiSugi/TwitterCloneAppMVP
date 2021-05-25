package com.example.twittercloneappmvp.feature.search_result.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract

class SearchResultViewHolder(
    private val view: View,
    private val viewProxy: SearchResultContract.ViewHolderViewProxy = ViewHolderViewProxy(view)
) : RecyclerView.ViewHolder(view) {

    fun bind(tweet: Tweet, listener: SearchResultContract.IconClickListener?) {
        viewProxy.run {
            loadImageToView(tweet.user.profileImageUrlHttps)
            setName(tweet.user.name)
            setUserName(tweet.user.userName)
            setText(tweet.text)
            setOnClickListener(tweet.user, listener)
        }
    }

    internal class ViewHolderViewProxy(
        private val view: View,
        private val requestManager: RequestManager = Glide.with(view)
    ) : SearchResultContract.ViewHolderViewProxy {

        private val iconIv: ImageView
            get() = view.findViewById(R.id.icon)

        private val nameTv: TextView
            get() = view.findViewById(R.id.name)

        private val userNameTv: TextView
            get() = view.findViewById(R.id.user_name)

        private val textTv: TextView
            get() = view.findViewById(R.id.tweet_text)

        override fun loadImageToView(imageUrl: String) {
            requestManager.load(imageUrl).into(iconIv)
        }

        override fun setName(name: String) {
            nameTv.text = name
        }

        override fun setUserName(userName: String) {
            userNameTv.text = userName
        }

        override fun setText(text: String) {
            textTv.text = text
        }

        override fun setOnClickListener(
            user: User,
            listener: SearchResultContract.IconClickListener?
        ) {
            iconIv.setOnClickListener { listener?.onIconClick(user) }
        }
    }
}

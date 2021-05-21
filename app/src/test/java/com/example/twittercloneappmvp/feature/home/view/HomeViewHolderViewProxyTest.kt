package com.example.twittercloneappmvp.feature.home.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.example.common_api.home_timeline.ResponseUser
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class HomeViewHolderViewProxyTest {

    private lateinit var viewProxy: HomeViewHolder.ViewHolderViewProxy
    private val requestManager: RequestManager = mock()
    private val iconIv: ImageView = mock()
    private val nameTv: TextView = mock()
    private val screenNameTv: TextView = mock()
    private val textTv: TextView = mock()

    private val view: View = mock {
        on { findViewById<ImageView>(R.id.user_icon) } doReturn iconIv
        on { findViewById<TextView>(R.id.user_name) } doReturn nameTv
        on { findViewById<TextView>(R.id.user_screen_name) } doReturn screenNameTv
        on { findViewById<TextView>(R.id.tweet_text) } doReturn textTv
    }

    @BeforeEach
    fun setUp() {
        viewProxy = HomeViewHolder.ViewHolderViewProxy(view, requestManager)
    }

    @Test
    fun `loadImageToView should load image into iconIv`() {
        val imageUrl = "imageUrl"
        val requestBuilder: RequestBuilder<Drawable> = mock()
        doReturn(requestBuilder).whenever(requestManager).load(imageUrl)

        viewProxy.loadImageToView(imageUrl)

        verify(requestBuilder).into(iconIv)
    }

    @Test
    fun `setName should set name into nameTv`() {
        val name = "name"

        viewProxy.setName(name)

        verify(nameTv).text = name
    }

    @Test
    fun `setScreenName should set screenName into screenNameTv`() {
        val screenName = "screenNameTv"

        viewProxy.setScreenName(screenName)

        verify(screenNameTv).text = screenName
    }

    @Test
    fun `setText should set text into textTv`() {
        val text = "text"

        viewProxy.setText(text)

        verify(textTv).text = text
    }

    @Test
    fun `setOnClickListener should set the listener to iconIv`() {
        val user: ResponseUser = mock()
        val listener: HomeContract.IconClickListener = mock()

        viewProxy.setOnClickListener(user, listener)

        val clickListener = argumentCaptor<View.OnClickListener>() {
            verify(iconIv).setOnClickListener(capture())
        }.firstValue
        clickListener.onClick(view)

        verify(listener).onIconClick(user)
    }
}

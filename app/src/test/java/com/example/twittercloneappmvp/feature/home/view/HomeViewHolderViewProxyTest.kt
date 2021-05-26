package com.example.twittercloneappmvp.feature.home.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.example.twittercloneappmvp.R
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class HomeViewHolderViewProxyTest {

    private lateinit var viewProxy: HomeViewHolder.ViewHolderViewProxy
    private val requestManager: RequestManager = mock()
    private val iconIv: ImageView = mock()
    private val nameTv: TextView = mock()
    private val userNameTv: TextView = mock()
    private val textTv: TextView = mock()

    private val view: View = mock {
        on { findViewById<ImageView>(R.id.icon) } doReturn iconIv
        on { findViewById<TextView>(R.id.name) } doReturn nameTv
        on { findViewById<TextView>(R.id.user_name) } doReturn userNameTv
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
    fun `setUserName should set userName into userNameTv`() {
        val userName = "userName"

        viewProxy.setUserName(userName)

        verify(userNameTv).text = "@$userName"
    }

    @Test
    fun `setText should set text into textTv`() {
        val text = "text"

        viewProxy.setText(text)

        verify(textTv).text = text
    }

    @Test
    fun `setOnClickListener should set the listener to iconIv`() {
        val user: User = mock()
        val listener: HomeContract.IconClickListener = mock()

        viewProxy.setOnClickListener(user, listener)

        val clickListener = argumentCaptor<View.OnClickListener>() {
            verify(iconIv).setOnClickListener(capture())
        }.firstValue
        clickListener.onClick(view)

        verify(listener).onIconClick(user)
    }
}

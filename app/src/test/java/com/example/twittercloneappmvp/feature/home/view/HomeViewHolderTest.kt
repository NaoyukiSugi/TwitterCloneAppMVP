package com.example.twittercloneappmvp.feature.home.view

import android.view.View
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class HomeViewHolderTest {

    private lateinit var viewHolder: HomeViewHolder
    private val view: View = mock()
    private val viewProxy: HomeContract.ViewHolderViewProxy = mock()
    private val user = User(
        id = 1L,
        name = "name",
        userName = "userName",
        description = "description",
        profileImageUrlHttps = "profileImageUrlHttps"
    )
    private val tweet = Tweet(
        id = 1L,
        createdAt = "createdAt",
        text = "text",
        user = user
    )
    private val listener: HomeContract.IconClickListener = mock()


    @BeforeEach
    fun setUp() {
        viewHolder = HomeViewHolder(view, viewProxy)
    }

    @Test
    fun `bind should call loadImageToView`() {
        viewHolder.bind(tweet, listener)

        verify(viewProxy).loadImageToView(tweet.user.profileImageUrlHttps)
    }

    @Test
    fun `bind should call setName`() {
        viewHolder.bind(tweet, listener)

        verify(viewProxy).setName(tweet.user.name)
    }

    @Test
    fun `bind should call setUserName`() {
        viewHolder.bind(tweet, listener)

        verify(viewProxy).setUserName(tweet.user.userName)
    }

    @Test
    fun `bind should call setText`() {
        viewHolder.bind(tweet, listener)

        verify(viewProxy).setText(tweet.text)
    }

    @Test
    fun `bind should call setOnClickListener`() {
        viewHolder.bind(tweet, listener)

        verify(viewProxy).setOnClickListener(tweet.user, listener)
    }
}

package com.example.twittercloneappmvp.search_result.view

import android.view.View
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import com.example.twittercloneappmvp.search_result.contract.SearchResultContract
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SearchResultViewHolderTest {

    private lateinit var viewHolder: SearchResultViewHolder
    private val view: View = mock()
    private val viewProxy: SearchResultContract.ViewHolderViewProxy = mock()
    private val user = User(
        id = 1L,
        name = "name",
        screenName = "screenName",
        description = "description",
        profileImageUrlHttps = "profileImageUrlHttps"
    )
    private val tweet = Tweet(
        id = 1L,
        createdAt = "createdAt",
        text = "text",
        user = user
    )
    private val listener: SearchResultContract.IconClickListener = mock()

    @BeforeEach
    fun setUp() {
        viewHolder = SearchResultViewHolder(view, viewProxy)
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
    fun `bind should call setScreenName`() {
        viewHolder.bind(tweet, listener)

        verify(viewProxy).setScreenName(tweet.user.screenName)
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

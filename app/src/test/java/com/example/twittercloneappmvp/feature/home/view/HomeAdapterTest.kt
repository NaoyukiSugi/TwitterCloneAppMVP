package com.example.twittercloneappmvp.feature.home.view

import android.view.ViewGroup
import com.example.common_api.home_timeline.Tweet
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class HomeAdapterTest {

    private lateinit var adapter: HomeAdapter
    val viewHolder: HomeViewHolder = mock()

    @BeforeEach
    fun setUp() {
        adapter = spy(HomeAdapter())
    }

    @Test
    fun `onCreateViewHolder should return HomeViewHolder`() {
        val parent: ViewGroup = mock()
        val viewType = 0
        doReturn(viewHolder).whenever(adapter).createHomeViewHolder(parent)

        val result = adapter.onCreateViewHolder(parent, viewType)

        assertEquals(viewHolder, result)
    }

    // region onBindViewHolder
    @Test
    fun `onBindViewHolder should call bind when tweet is not null`() {
        val position = 0
        val tweet: Tweet = mock()
        doReturn(tweet).whenever(adapter).getTweet(position)

        adapter.onBindViewHolder(viewHolder, position)

        verify(viewHolder).bind(tweet, adapter.listener)
    }

    @Test
    fun `onBindViewHolder should not call bind when tweet is null`() {
        val position = 0
        doReturn(null).whenever(adapter).getTweet(position)

        adapter.onBindViewHolder(viewHolder, position)

        verify(viewHolder, never()).bind(any(), any())
    }
    // endregion

    // region areItemsTheSame
    @Test
    fun `areItemsTheSame should return true when tweet ids are same`() {
        val diffCallBack = HomeAdapter.DIFF_CALLBACK
        val oldItem: Tweet = mock()
        doReturn(0L).whenever(oldItem).id
        val newItem: Tweet = mock()
        doReturn(0L).whenever(newItem).id

        val result = diffCallBack.areItemsTheSame(oldItem, newItem)

        assertTrue(result)
    }

    @Test
    fun `areItemsTheSame should return false when tweet ids are not same`() {
        val diffCallBack = HomeAdapter.DIFF_CALLBACK
        val oldItem: Tweet = mock()
        doReturn(0L).whenever(oldItem).id
        val newItem: Tweet = mock()
        doReturn(1L).whenever(newItem).id

        val result = diffCallBack.areItemsTheSame(oldItem, newItem)

        assertFalse(result)
    }
    // endregion

    // region areContentsTheSame
    @Test
    fun `areContentsTheSame should return true when tweet instance are same`() {
        val diffCallBack = HomeAdapter.DIFF_CALLBACK
        val oldItem: Tweet = mock()
        val newItem: Tweet = oldItem

        val result = diffCallBack.areContentsTheSame(oldItem, newItem)

        assertTrue(result)
    }

    @Test
    fun `areContentsTheSame should return false when tweet instance are not same`() {
        val diffCallBack = HomeAdapter.DIFF_CALLBACK
        val oldItem: Tweet = mock()
        val newItem: Tweet = mock()

        val result = diffCallBack.areContentsTheSame(oldItem, newItem)

        assertFalse(result)
    }
    // endregion
}

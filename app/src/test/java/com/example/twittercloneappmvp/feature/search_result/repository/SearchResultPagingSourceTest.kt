package com.example.twittercloneappmvp.feature.search_result.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common_api.search_result.ResponseMeta
import com.example.common_api.search_result.ResponseTweet
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.twittercloneappmvp.model.Tweet
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import retrofit2.Response

internal class SearchResultPagingSourceTest {

    private lateinit var pagingSource: SearchResultPagingSource
    private val api: SearchResultTimelineApi = mock()
    private val searchQuery = "searchQuery"
    private val params: PagingSource.LoadParams<String> = mock()

    @BeforeEach
    fun setUp() {
        pagingSource = spy(SearchResultPagingSource(api, searchQuery))
    }

    @Test
    fun `getRefreshKey should return null`() {
        val state: PagingState<String, Tweet> = mock()

        val result = pagingSource.getRefreshKey(state)

        assertNull(result)
    }

    // region load
    @Test
    fun `load should call getSearchResultTimeline of api`() {
        runBlocking {
            pagingSource.load(params)

            verify(api).getSearchResultTimeline(searchQuery = searchQuery, nextToken = params.key)
        }
    }

    @Test
    fun `load should return LoadResult Page when response is successful`() {
        runBlocking {
            val responseTweets: List<ResponseTweet> = mock()
            val convertedTweets: List<Tweet> = mock()
            val nextToken = "nextToken"
            val response: SearchResultTimelineResponse = mock {
                on { tweets } doReturn responseTweets
                on { meta } doReturn ResponseMeta(nextToken)
            }
            val apiResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn response
            }
            doReturn(nextToken).whenever(params).key
            doReturn(apiResponse)
                .whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = params.key)
            doReturn(convertedTweets).whenever(pagingSource).convertToTweet(response)
            val page = PagingSource.LoadResult.Page(
                data = convertedTweets,
                prevKey = null,
                nextKey = response.meta.nextToken
            )

            val result = pagingSource.load(params)

            assertEquals(page, result)
        }
    }

    @Test
    fun `load should return LoadResult Error when response is not successful`() {
        runBlocking {
            val apiResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn false
            }
            doReturn(apiResponse)
                .whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = "nextToken")

            val result = pagingSource.load(params)

            assertTrue(result is PagingSource.LoadResult.Error)
        }
    }
    // endregion
}

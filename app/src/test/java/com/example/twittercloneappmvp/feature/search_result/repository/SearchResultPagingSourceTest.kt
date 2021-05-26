package com.example.twittercloneappmvp.feature.search_result.repository

import androidx.paging.PagingSource.*
import androidx.paging.PagingState
import com.example.common_api.search_result.ResponseMeta
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.twittercloneappmvp.model.Tweet
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import retrofit2.HttpException
import retrofit2.Response

internal class SearchResultPagingSourceTest {

    private lateinit var pagingSource: SearchResultPagingSource
    private val api: SearchResultTimelineApi = mock()
    private val searchQuery = "searchQuery"
    private val nextToken = "nextToken"
    private val params =
        LoadParams.Refresh(key = nextToken, loadSize = 1, placeholdersEnabled = false)

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
    fun `load should return LoadResult Page when response is successful`() {
        runBlocking {
            val response = SearchResultTimelineResponse(
                tweets = mock(),
                meta = ResponseMeta(nextToken),
                includes = mock()
            )
            val apiResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn response
            }
            val convertedTweets: List<Tweet> = mock()
            val page = LoadResult.Page(
                data = convertedTweets,
                prevKey = null,
                nextKey = response.meta.nextToken
            )
            doReturn(apiResponse)
                .whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
            doReturn(convertedTweets).whenever(pagingSource).convertToTweet(response)

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
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)

            val result = pagingSource.load(params)

            assertTrue(result is LoadResult.Error)
        }
    }
    // endregion

    // region fetch
    @Test
    fun `fetch should return SearchResultTimelineResponse when response is successful`() {
        runBlocking {
            val response: SearchResultTimelineResponse = mock()
            val apiResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn response
            }
            doReturn(apiResponse)
                .whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)

            val result = pagingSource.fetch(searchQuery, nextToken)

            assertEquals(response, result)
        }
    }

    @Test
    fun `fetch should throw HttpException when response is not successful`() {
        runBlocking {
            val apiResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn false
            }
            doReturn(apiResponse)
                .whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)

            runCatching {
                pagingSource.fetch(searchQuery, nextToken)
            }.getOrElse {
                assertTrue(it is HttpException)
            }
        }
    }
    // end region
}

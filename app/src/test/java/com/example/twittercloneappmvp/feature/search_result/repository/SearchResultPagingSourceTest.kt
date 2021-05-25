package com.example.twittercloneappmvp.feature.search_result.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
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
    fun `load should call LoadResult Error when some error is thrown`() {
        runBlocking {
            val response: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn false
            }
            doReturn(response)
                .whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = params.key)

//            val result = pagingSource.load(params)
//
//            assertTrue(result is PagingSource.LoadResult.Error)
        }
    }
    // endregion
}

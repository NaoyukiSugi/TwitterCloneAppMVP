package com.example.twittercloneappmvp.search_result.repository

import com.example.common_api.search_result.SearchQuery
import com.example.common_api.search_result.SearchResultResponse
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.util.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class SearchResultRepositoryTest {

    private lateinit var repository: SearchResultRepository
    private val api: SearchResultTimelineApi = mock()

    @BeforeEach
    fun setUp() {
        repository = SearchResultRepository(api)
    }

    @Test
    fun `getSearchResultTimeline should emit Success when response is successful`() {
        runBlocking {
            val searchQuery = SearchQuery("searchQuery")
            val nextToken = "nextToken"

            val searchResultResponse: SearchResultResponse = mock()
            val retrofitResponse: Response<SearchResultResponse> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn searchResultResponse
            }
            doReturn(retrofitResponse).whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery.value, nextToken = nextToken)

            repository.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
                .collect {
                    assertTrue(it is Result.Success<SearchResultResponse>)
                    assertEquals(searchResultResponse, it.data)
                }
        }
    }

    @Test
    fun `getSearchResultTimeline should emit Error when response is not successful`() {
        runBlocking {
            val searchQuery = SearchQuery("searchQuery")
            val nextToken = "nextToken"

            val message = "message"
            val retrofitResponse: Response<SearchResultResponse> = mock {
                on { isSuccessful } doReturn false
                on { message() } doReturn message
            }
            doReturn(retrofitResponse).whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery.value, nextToken = nextToken)

            repository.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
                .collect {
                    assertTrue(it is Result.Error<SearchResultResponse>)
                    assertEquals(message, it.message)
                }
        }
    }

}

package com.example.twittercloneappmvp.feature.search_result.repository

import com.example.common_api.search_result.SearchQuery
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.util.NetworkResult
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

            val searchResultTimelineResponse: SearchResultTimelineResponse = mock()
            val retrofitTimelineResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn searchResultTimelineResponse
            }
            doReturn(retrofitTimelineResponse).whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery.value, nextToken = nextToken)

            repository.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
                .collect {
                    assertTrue(it is NetworkResult.Success<SearchResultTimelineResponse>)
                    assertEquals(searchResultTimelineResponse, it.data)
                }
        }
    }

    @Test
    fun `getSearchResultTimeline should emit Error when response is not successful`() {
        runBlocking {
            val searchQuery = SearchQuery("searchQuery")
            val nextToken = "nextToken"

            val message = "message"
            val retrofitTimelineResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn false
                on { message() } doReturn message
            }
            doReturn(retrofitTimelineResponse).whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery.value, nextToken = nextToken)

            repository.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
                .collect {
                    assertTrue(it is NetworkResult.Error<SearchResultTimelineResponse>)
                    assertEquals(message, it.message)
                }
        }
    }
}

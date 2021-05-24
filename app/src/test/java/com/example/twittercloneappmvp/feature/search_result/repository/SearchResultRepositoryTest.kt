package com.example.twittercloneappmvp.feature.search_result.repository

import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.model.Future
import com.example.twittercloneappmvp.model.Tweet
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import retrofit2.HttpException
import retrofit2.Response
import java.lang.NullPointerException

class SearchResultRepositoryTest {

    private lateinit var repository: SearchResultRepository
    private val api: SearchResultTimelineApi = mock()
    private val searchQuery = "searchQuery"
    private val nextToken = "nextToken"

    @BeforeEach
    fun setUp() {
        repository = spy(SearchResultRepository(api))
    }

    // region getSearchResultTimeline
    @Test
    fun `getSearchResultTimeline should call getSearchResultTimeline of api`() {
        runBlocking {
            val response: Response<SearchResultTimelineResponse> = mock()
            doReturn(response).whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)

            val result = repository.getSearchResultTimeline(searchQuery, nextToken)

            result.collect {
                verify(api)
                    .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
            }
        }
    }


    @Test
    fun `getSearchResultTimeline should emit Success when response is successful`() {
        runBlocking {
            val searchResultTimelineResponse: SearchResultTimelineResponse = mock()
            val apiResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn searchResultTimelineResponse
            }
            val tweets: List<Tweet> = listOf(mock())
            doReturn(apiResponse)
                .whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
            doReturn(tweets).whenever(repository).convertToTweet(searchResultTimelineResponse)

            val result =
                repository.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)

            result.collect {
                it.transform { tweetList ->
                    assertEquals(tweets, tweetList)
                }
            }
        }
    }

    @Test
    fun `getSearchResultTimeline should emit Error of HttpException when response is not successful`() {
        runBlocking {
            val apiResponse: Response<SearchResultTimelineResponse> = mock {
                on { isSuccessful } doReturn false
            }
            doReturn(apiResponse)
                .whenever(api)
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)

            val result =
                repository.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)

            result.catch { error ->
                assertTrue(error is HttpException)
            }
        }
    }

    @Test
    fun `getSearchResultTimeline should emit Error of NullPointerException when response is null`() {
        runBlocking {
            val result =
                repository.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)

            result.catch { error ->
                assertTrue(error is NullPointerException)
            }
        }
    }

    @Test
    fun `getSearchResultTimeline should emit Proceeding when starting`() {
        runBlocking {
            val result = repository
                .getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
                .first()

            assertTrue(result is Future.Proceeding)
        }
    }
    // endregion
}

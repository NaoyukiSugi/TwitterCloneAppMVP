package com.example.twittercloneappmvp.feature.home.repository

import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.common_api.home_timeline.ResponseTweet
import com.example.twittercloneappmvp.model.Future
import com.example.twittercloneappmvp.model.Tweet
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*
import retrofit2.HttpException
import retrofit2.Response

class HomeRepositoryTest {

    private lateinit var repository: HomeRepository
    private val api: HomeTimelineApi = mock()

    @BeforeEach
    fun setUp() {
        repository = spy(HomeRepository(api))
    }

    // region getHomeTimeline
    @Test
    fun `getHomeTimeline should call getHomeTimeline of api`() {
        runBlocking {
            val response: Response<List<ResponseTweet>> = mock()
            doReturn(response).whenever(api).getHomeTimeline()

            val result = repository.getHomeTimeline()

            result.collect {
                verify(api).getHomeTimeline()
            }
        }
    }

    @Test
    fun `getHomeTimeline should emit Success when response is successful`() {
        runBlocking {
            val responseTweet = ResponseTweet(
                id = 0L,
                createdAt = "createdAt",
                text = "text",
                user = mock()
            )
            val responseTweetList: List<ResponseTweet> = listOf(responseTweet)
            val response: Response<List<ResponseTweet>> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn responseTweetList
            }
            val tweet: Tweet = mock()
            doReturn(response).whenever(api).getHomeTimeline()
            doReturn(tweet).whenever(repository).convertToTweet(responseTweet)

            val result = repository.getHomeTimeline()

            result.collect {
                it.transform { tweetList ->
                    assertEquals(listOf(tweet), tweetList)
                }
            }
        }
    }

    @Test
    fun `getHomeTimeline should emit Error of HttpException when response is not successful`() {
        runBlocking {
            val response: Response<List<ResponseTweet>> = mock {
                on { isSuccessful } doReturn false
            }
            doReturn(response).whenever(api).getHomeTimeline()

            val result = repository.getHomeTimeline()

            result.catch { error ->
                assertTrue(error is HttpException)
            }
        }
    }

    @Test
    fun `getHomeTimeline should emit Error of NullPointerException when response is null`() {
        runBlocking {
            val result = repository.getHomeTimeline()

            result.catch { error ->
                assertTrue(error is NullPointerException)
            }
        }
    }

    @Test
    fun `getHomeTimeline should emit Proceeding when starting`() {
        runBlocking {
            val response: Response<List<ResponseTweet>> = mock {}
            doReturn(response).whenever(api).getHomeTimeline()

            val result = repository.getHomeTimeline().first()

            assertTrue(result is Future.Proceeding)
        }
    }
    // endregion
}

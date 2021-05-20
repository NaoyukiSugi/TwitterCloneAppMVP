package com.example.twittercloneappmvp.home.repository

import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.common_api.home_timeline.Tweet
import com.example.twittercloneappmvp.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class HomeRepositoryTest {

    private lateinit var repository: HomeRepository
    private val api: HomeTimelineApi = mock()

    @BeforeEach
    fun setUp() {
        repository = HomeRepository(api)
    }

    @Test
    fun `getHomeTimeline should return Success when response is successful`() {
        runBlocking {
            val tweetList: List<Tweet> = mock()
            val response: Response<List<Tweet>> = mock {
                on { isSuccessful } doReturn true
                on { body() } doReturn tweetList
            }
            doReturn(response).whenever(api).getHomeTimeline()

            val result = repository.getHomeTimeline()

            assertTrue(result is Result.Success<List<Tweet>>)
            assertEquals(tweetList, result.data)
        }
    }

    @Test
    fun `getHomeTimeline should return Error when response is not successful`() {
        runBlocking {
            val message = "message"
            val response: Response<List<Tweet>> = mock {
                on { isSuccessful } doReturn false
                on { message() } doReturn message
            }
            doReturn(response).whenever(api).getHomeTimeline()

            val result = repository.getHomeTimeline()

            assertTrue(result is Result.Error<List<Tweet>>)
            assertEquals(message, result.message)
        }
    }
}

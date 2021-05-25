package com.example.twittercloneappmvp.feature.search_result.repository

import androidx.paging.Pager
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.model.Tweet
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class SearchResultRepositoryTest {

    private lateinit var repository: SearchResultRepository
    private val api: SearchResultTimelineApi = mock()

    @BeforeEach
    fun setUp() {
        repository = spy(SearchResultRepository(api))
    }

    @Test
    fun `getSearchResultTimeline should return flow of Pager`() {
        val pager: Pager<String, Tweet> = mock()
        val searchQuery = "searchQuery"
        doReturn(pager).whenever(repository).createPager(searchQuery)

        val result = repository.getSearchResultTimeline(searchQuery)

        assertEquals(pager.flow, result)
    }
}

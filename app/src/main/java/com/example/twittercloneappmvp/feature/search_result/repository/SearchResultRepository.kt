package com.example.twittercloneappmvp.feature.search_result.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.model.Tweet
import kotlinx.coroutines.flow.*

class SearchResultRepository(
    private val api: SearchResultTimelineApi
) : SearchResultContract.Repository {

    override fun getSearchResultTimeline(searchQuery: String): Flow<PagingData<Tweet>> {
        return Pager(
            config = pagingConfig(),
            pagingSourceFactory = { createSearchResultPagingSource(searchQuery) }
        ).flow
    }

    private fun pagingConfig() =
        PagingConfig(
            pageSize = GET_SEARCH_RESULT_TIMELINE_SIZE,
            enablePlaceholders = true
        )

    @VisibleForTesting
    internal fun createSearchResultPagingSource(searchQuery: String) =
        SearchResultPagingSource(api, searchQuery)

    companion object {
        private const val GET_SEARCH_RESULT_TIMELINE_SIZE = 10
    }
}

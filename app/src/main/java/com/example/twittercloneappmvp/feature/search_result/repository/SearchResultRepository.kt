package com.example.twittercloneappmvp.feature.search_result.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.model.Tweet
import kotlinx.coroutines.flow.*

class SearchResultRepository(
    private val pagingSource: SearchResultPagingSource
) : SearchResultContract.Repository {

    override fun getSearchResultTimeline(
        searchQuery: String,
        nextToken: String?
    ): Flow<PagingData<Tweet>> {
        return Pager(
            config = pagingConfig(),
            pagingSourceFactory = { pagingSource }
        ).flow
    }

    private fun pagingConfig() =
        PagingConfig(
            pageSize = GET_SEARCH_RESULT_TIMELINE_SIZE,
            enablePlaceholders = true
        )

    companion object {
        private const val GET_SEARCH_RESULT_TIMELINE_SIZE = 10
    }
}

package com.example.twittercloneappmvp.feature.search_result.repository

import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.model.Future
import com.example.twittercloneappmvp.util.apiFlow
import kotlinx.coroutines.flow.Flow

class SearchResultRepository(
    private val api: SearchResultTimelineApi
) : SearchResultContract.Repository {

    override fun getSearchResultTimeline(
        searchQuery: String,
        nextToken: String?
    ): Flow<Future<SearchResultTimelineResponse>> =
        apiFlow { api.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken) }
}

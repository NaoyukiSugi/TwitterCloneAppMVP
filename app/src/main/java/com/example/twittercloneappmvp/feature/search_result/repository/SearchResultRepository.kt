package com.example.twittercloneappmvp.feature.search_result.repository

import com.example.common_api.search_result.SearchQuery
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchResultRepository(
    private val api: SearchResultTimelineApi
) : SearchResultContract.Repository {

    override fun getSearchResultTimeline(
        searchQuery: SearchQuery,
        nextToken: String?
    ): Flow<NetworkResult<SearchResultTimelineResponse>> = flow {
        val response = api.getSearchResultTimeline(
            searchQuery = searchQuery.value,
            nextToken = nextToken
        )
        if (response.isSuccessful) {
            response.body()?.let { emit(NetworkResult.Success(it)) }
        } else {
            emit(NetworkResult.Error<SearchResultTimelineResponse>(response.message()))
        }
    }.flowOn(Dispatchers.IO)
}

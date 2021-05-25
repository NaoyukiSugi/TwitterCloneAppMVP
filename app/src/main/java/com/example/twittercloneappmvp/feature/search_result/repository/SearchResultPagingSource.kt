package com.example.twittercloneappmvp.feature.search_result.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import retrofit2.HttpException
import java.lang.Exception

class SearchResultPagingSource(
    private val api: SearchResultTimelineApi,
    private val searchQuery: String
) : PagingSource<String, Tweet>() {

    override fun getRefreshKey(state: PagingState<String, Tweet>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Tweet> {
        return try {
            val response =
                api.getSearchResultTimeline(searchQuery = searchQuery, nextToken = params.key)
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = convertToTweet(response.body()!!),
                    prevKey = null,
                    nextKey = response.body()!!.meta.nextToken
                )
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @VisibleForTesting
    internal fun convertToTweet(searchResultTimelineResponse: SearchResultTimelineResponse): List<Tweet> {
        return searchResultTimelineResponse.let { response ->
            val responseUserIdMap = response.includes.users.associateBy { it.id }
            response.tweets.map {
                val responseUser = responseUserIdMap[it.authorId]!!
                Tweet(
                    id = it.id,
                    createdAt = it.createdAt,
                    text = it.text,
                    user = User(
                        id = responseUser.id,
                        name = responseUser.name,
                        screenName = responseUser.screenName,
                        description = responseUser.description,
                        profileImageUrlHttps = responseUser.profileImageUrlHttps
                    )
                )
            }
        }
    }
}
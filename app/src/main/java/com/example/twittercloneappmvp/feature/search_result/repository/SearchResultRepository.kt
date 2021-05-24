package com.example.twittercloneappmvp.feature.search_result.repository

import androidx.annotation.VisibleForTesting
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.model.Future
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

class SearchResultRepository(
    private val api: SearchResultTimelineApi
) : SearchResultContract.Repository {

    override fun getSearchResultTimeline(
        searchQuery: String,
        nextToken: String?
    ): Flow<Future<List<Tweet>>> =
        flow<Future<List<Tweet>>> {
            val response =
                api.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
            if (response.isSuccessful) emit(Future.Success(convertToTweet(response.body()!!)))
            else throw HttpException(response)
        }.catch {
            emit(Future.Error(it))
        }.onStart {
            emit(Future.Proceeding)
        }.flowOn(Dispatchers.IO)

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

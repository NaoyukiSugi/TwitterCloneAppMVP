package com.example.twittercloneappmvp.feature.search_result.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common_api.search_result.SearchResultTimelineResponse
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.feature.search_result.fragment.SearchResultFragmentArgs
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import com.example.twittercloneappmvp.util.NetworkResult
import com.example.twittercloneappmvp.util.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchResultViewModel(
    private val repository: SearchResultContract.Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs: SearchResultFragmentArgs by navArgs(savedStateHandle)

    @VisibleForTesting
    internal lateinit var searchResultTimelineResult: MutableLiveData<List<Tweet>>

    fun fetch(nextToken: String): SearchResultTimelineResponse? {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchQuery = navArgs.searchQuery
                repository.getSearchResultTimeline(searchQuery = searchQuery, nextToken = nextToken)
                    .collect { networkResult ->
                        when (networkResult) {
                            is NetworkResult.Success -> {
                                val response = networkResult.data
                                if (response != null) {
                                    val tweets = convertToTweets(networkResult.data)
                                    if (tweets.isNotEmpty()) {
                                        searchResultTimelineResult.postValue(tweets)
                                    }
                                } else {

                                }
                            }
                            is NetworkResult.Error -> {

                            }
                        }
                    }
            } catch (e: Exception) {

            }
        }
    }

    fun convertToTweets(searchResultTimelineResponse: SearchResultTimelineResponse): List<Tweet> {
        return searchResultTimelineResponse.let { response ->
            val userIdMap = response.includes.responseUsers.associateBy { it.id }
            response.responseTweets.map {
                val responseUser = userIdMap[it.authorId]
                Tweet(
                    id = it.id,
                    createdAt = it.createdAt,
                    text = it.text,
                    user = User(
                        id = responseUser!!.id,
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

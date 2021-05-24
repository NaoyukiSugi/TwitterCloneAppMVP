package com.example.twittercloneappmvp.feature.home.repository

import androidx.annotation.VisibleForTesting
import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.common_api.home_timeline.ResponseTweet
import com.example.twittercloneappmvp.feature.home.contract.HomeContract
import com.example.twittercloneappmvp.model.Future
import com.example.twittercloneappmvp.model.Tweet
import com.example.twittercloneappmvp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeTimelineApi
) : HomeContract.Repository {

    override fun getHomeTimeline(): Flow<Future<List<Tweet>>> =
        flow<Future<List<Tweet>>> {
            val response = api.getHomeTimeline()
            if (response.isSuccessful) emit(Future.Success(response.body()!!.map(::convertToTweet)))
            else throw HttpException(response)
        }.catch {
            emit(Future.Error(it))
        }.onStart {
            emit(Future.Proceeding)
        }.flowOn(Dispatchers.IO)

    @VisibleForTesting
    internal fun convertToTweet(responseTweet: ResponseTweet): Tweet =
        Tweet(
            id = responseTweet.id,
            createdAt = responseTweet.createdAt,
            text = responseTweet.text,
            user = User(
                id = responseTweet.user.id,
                name = responseTweet.user.name,
                screenName = responseTweet.user.screenName,
                description = responseTweet.user.description,
                profileImageUrlHttps = responseTweet.user.profileImageUrlHttps
            )
        )
}

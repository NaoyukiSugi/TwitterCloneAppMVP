package com.example.common_api.search_result

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResultTimelineResponse(
    @SerializedName("data")
    val responseTweets: List<ResponseTweet>,
    @SerializedName("includes")
    val includes: ResponseUserList,
    @SerializedName("meta")
    val meta: Meta
) : Serializable

data class ResponseTweet(
    @SerializedName("id")
    val id: Long,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("author_id")
    val authorId: Long
) : Serializable

data class ResponseUserList(
    @SerializedName("users")
    val responseUsers: List<ResponseUser>
) : Serializable

data class ResponseUser(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("screen_name")
    val screenName: String,
    @SerializedName(value = "description", alternate = ["username"])
    val description: String,
    @SerializedName(value = "profile_image_url_https", alternate = ["profile_image_url"])
    val profileImageUrlHttps: String,
) : Serializable

data class Meta(
    @SerializedName("next_token")
    val nextToken: String?
) : Serializable

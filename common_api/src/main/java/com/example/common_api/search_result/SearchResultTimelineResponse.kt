package com.example.common_api.search_result

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResultTimelineResponse(
    @SerializedName("data")
    val tweets: List<ResponseTweet>,
    @SerializedName("includes")
    val includes: ResponseUserList,
    @SerializedName("meta")
    val meta: ResponseMeta
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
    val users: List<ResponseUser>
) : Serializable

data class ResponseUser(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("profile_image_url")
    val profileImageUrlHttps: String,
) : Serializable

data class ResponseMeta(
    @SerializedName("next_token")
    val nextToken: String?
) : Serializable

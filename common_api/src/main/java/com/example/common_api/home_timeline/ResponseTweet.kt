package com.example.common_api.home_timeline

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseTweet(
    @SerializedName("id")
    val id: Long,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("user")
    val user: ResponseUser
) : Serializable

data class ResponseUser(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("screen_name")
    val screenName: String,
    @SerializedName(value = "description")
    val description: String,
    @SerializedName("profile_image_url_https")
    val profileImageUrlHttps: String,
) : Serializable

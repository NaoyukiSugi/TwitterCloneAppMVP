package com.example.twittercloneappmvp.home.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tweet(
    @SerializedName("id")
    val id: Long,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("user")
    val user: User
) : Serializable

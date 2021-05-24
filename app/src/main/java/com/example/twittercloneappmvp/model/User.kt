package com.example.twittercloneappmvp.model

import java.io.Serializable

data class User(
    val id: Long,
    val name: String,
    val screenName: String,
    val description: String,
    val profileImageUrlHttps: String,
) : Serializable

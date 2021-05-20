package com.example.twittercloneappmvp.model

data class User(
    val id: Long,
    val name: String,
    val screenName: String,
    val description: String,
    val profileImageUrlHttps: String,
)

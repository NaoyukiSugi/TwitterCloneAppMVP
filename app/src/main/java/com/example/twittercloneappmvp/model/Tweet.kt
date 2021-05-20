package com.example.twittercloneappmvp.model

data class Tweet(
    val id: Long,
    val createdAt: String,
    val text: String,
    val user: User
)

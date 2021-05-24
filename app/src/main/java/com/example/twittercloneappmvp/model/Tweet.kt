package com.example.twittercloneappmvp.model

import java.io.Serializable

data class Tweet(
    val id: Long,
    val createdAt: String,
    val text: String,
    val user: User
) : Serializable

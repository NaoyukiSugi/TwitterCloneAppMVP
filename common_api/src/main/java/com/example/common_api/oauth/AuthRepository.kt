package com.example.common_api.oauth

interface AuthRepository {
    fun getOAuthToken(): String?
    suspend fun fetchAuthToken(): String
}

package com.example.twittercloneappmvp.feature.splash.repository

import android.content.SharedPreferences
import com.example.common_api.oauth.AuthRepository
import com.example.common_api.oauth.TwitterAuthService

class TwitterAuthRepository(
    private val twitterAuthService: TwitterAuthService,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override fun getOAuthToken(): String? {
        return sharedPreferences.getString(KEY_OAUTH_TOKEN, null)
    }

    override suspend fun fetchAuthToken(): String {
        getOAuthToken()
        return twitterAuthService.getAuthToken().apply {
            sharedPreferences.edit().putString(KEY_OAUTH_TOKEN, this).apply()
        }
    }

    companion object {
        const val KEY_OAUTH_TOKEN = "oauth_token"
    }
}

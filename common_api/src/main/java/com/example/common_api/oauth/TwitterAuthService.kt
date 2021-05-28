package com.example.common_api.oauth

import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TwitterAuthService {
    @FormUrlEncoded
    @POST("oauth/request_token")
    suspend fun getAuthToken(): String
}

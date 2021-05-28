package com.example.common_api.oauth

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
    private val authRepository: AuthRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            authRepository.getOAuthToken()?.let { oAuthToken ->
                addHeader(AUTHORIZATION_HEADER_KEY, oAuthToken)
            }
        }.build()
        return chain.proceed(newRequest)
    }

    private companion object {
        private const val AUTHORIZATION_HEADER_KEY = "Authorization"
    }
}

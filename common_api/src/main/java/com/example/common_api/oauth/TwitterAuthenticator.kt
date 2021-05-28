package com.example.common_api.oauth

import com.example.common_api.model.Future
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TwitterAuthenticator(
    private val authRepository: AuthRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        when {
            response.request().header(AUTHORIZATION_HEADER) == null -> null
            response.retryCount() >= MAX_RETRY_COUNT -> null
            else -> runBlocking {
//                val authData = refreshToken?.let { authRepository.fetchAuthToken(refreshToken) }
//                when (authData) {
//                    is Future.Error -> TODO()
//                    is Future.Proceeding -> TODO()
//                    is Future.Success -> TODO()
//                    null -> TODO()
//                }
            }
        }
    }

    private fun Response.retryCount(): Int {
        var retryCount = 1
        var response = this
        while (response.priorResponse()?.also { response = it } != null) {
            retryCount++
        }
        return retryCount
    }

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val MAX_RETRY_COUNT = 3
    }
}

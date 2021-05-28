package com.example.common_api

import javax.inject.Inject

class Environment @Inject constructor(var serverConfig: ServerConfig) {
    fun getBaseUrl(): String {
        return serverConfig.apiBaseUrl()
    }
}

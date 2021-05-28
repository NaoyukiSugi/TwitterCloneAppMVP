package com.example.common_api

abstract class ServerConfig {
    protected abstract fun domain(): String
    protected abstract fun scheme(): String
    protected abstract fun apiHost(): String

    fun apiDomain(): String {
        return apiHost() + "." + domain()
    }

    fun apiBaseUrl(): String {
        return scheme() + "://" + apiDomain()
    }
}

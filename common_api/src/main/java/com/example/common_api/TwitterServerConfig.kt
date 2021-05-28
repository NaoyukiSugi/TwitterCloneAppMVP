package com.example.common_api

class TwitterServerConfig : ServerConfig() {
    override fun domain(): String {
        return DOMAIN
    }

    override fun scheme(): String {
        return API_SCHEME
    }

    override fun apiHost(): String {
        return API_HOST
    }

    private companion object {
        private const val API_HOST = "api"
        private const val DOMAIN = "twitter.com"
        private const val API_SCHEME = "https"
    }
}

package com.example.common_api.search_result

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SearchResultTimelineApi {
    @GET("2/tweets/search/recent")
    suspend fun getSearchResultTimeline(
        @Query("query") searchQuery: String,
        @Query("expansions") expansions: String = "author_id",
        @Query("tweet.fields") tweetField: String = "created_at",
        @QueryMap(encoded = true) userFields: Map<String, String> = mapOf("user.fields" to "profile_image_url,description"),
        @Query("next_token") nextToken: String? = null
    ): Response<SearchResultTimelineResponse>
}

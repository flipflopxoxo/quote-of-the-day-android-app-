package com.clydelizardo.quotes.api

import com.clydelizardo.quotes.api.model.QuoteListResponse
import com.clydelizardo.quotes.api.model.QuoteOfTheDayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteService {
    @GET("api/qotd")
    suspend fun quoteOfTheDay(): QuoteOfTheDayResponse

    @GET("api/quotes")
    suspend fun quoteList(
        @Query("filter") filter: String? = null,
        @Query("type") type: String? = null,
        @Query("page") page: Int? = null,
    ): QuoteListResponse
}
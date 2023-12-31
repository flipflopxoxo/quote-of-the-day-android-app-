package com.clydelizardo.quotes.repository

import com.clydelizardo.quotes.model.Quote

interface QuoteRepository {
    suspend fun getQuoteOfTheDay(): Result<Quote>

    suspend fun getQuoteList(pageNumber: Int? = null): Result<List<Quote>>
}

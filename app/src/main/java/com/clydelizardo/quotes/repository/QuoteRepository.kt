package com.clydelizardo.quotes.repository

import com.clydelizardo.quotes.repository.model.Quote

interface QuoteRepository {
    suspend fun getQuoteOfTheDay(): Quote
}
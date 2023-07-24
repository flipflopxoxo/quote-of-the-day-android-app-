package com.clydelizardo.quotes.database

import com.clydelizardo.quotes.repository.model.Quote
import kotlinx.coroutines.flow.Flow

interface SavedQuoteRepository {
    fun quotes(): Flow<List<Quote>>

    suspend fun saveQuote(quote: Quote)

    suspend fun deleteQuote(quote: Quote)

    suspend fun deleteQuotes(quoteList: List<Quote>)
}
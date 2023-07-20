package com.clydelizardo.quotes.repository

import com.clydelizardo.quotes.api.QuoteService
import com.clydelizardo.quotes.repository.model.Quote
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val quoteService: QuoteService,
) : QuoteRepository {
    override suspend fun getQuoteOfTheDay(): Quote {
        val quoteOfTheDay = quoteService.quoteOfTheDay().quote
        return Quote(
            id = quoteOfTheDay.id,
            content = quoteOfTheDay.body,
            author = quoteOfTheDay.author,
            tags = quoteOfTheDay.tags.toSet()
        )
    }
}
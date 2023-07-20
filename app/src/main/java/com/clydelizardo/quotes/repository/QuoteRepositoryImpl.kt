package com.clydelizardo.quotes.repository

import com.clydelizardo.quotes.api.QuoteService
import com.clydelizardo.quotes.repository.model.Quote
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val quoteService: QuoteService,
) : QuoteRepository {
    override suspend fun getQuoteOfTheDay(): Quote {
        val quoteOfTheDay = quoteService.quoteOfTheDay().quote
        return quote(quoteOfTheDay)
    }

    private fun quote(quoteOfTheDay: com.clydelizardo.quotes.api.model.Quote) = Quote(
        id = quoteOfTheDay.id,
        content = quoteOfTheDay.body,
        author = quoteOfTheDay.author,
        tags = quoteOfTheDay.tags.toSet()
    )

    override suspend fun getQuoteList(
        pageNumber: Int?,
        quoteListFilter: QuoteListFilter?,
    ): List<Quote> {
        return quoteService.quoteList(null, null, pageNumber).quotes.map(::quote)
    }
}
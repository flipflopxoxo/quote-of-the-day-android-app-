package com.clydelizardo.quotes.repository

import com.clydelizardo.quotes.api.QuoteService
import com.clydelizardo.quotes.model.Quote
import javax.inject.Inject
import com.clydelizardo.quotes.api.model.Quote as ApiQuote

class QuoteRepositoryImpl @Inject constructor(
    private val quoteService: QuoteService,
) : QuoteRepository {
    override suspend fun getQuoteOfTheDay(): Result<Quote> {
        return try {
            val quoteOfTheDay = quoteService.quoteOfTheDay().quote
            Result.success(quote(quoteOfTheDay))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun quote(quoteOfTheDay: ApiQuote) = Quote(
        id = quoteOfTheDay.id,
        content = quoteOfTheDay.body,
        author = quoteOfTheDay.author,
        tags = quoteOfTheDay.tags.toSet()
    )

    override suspend fun getQuoteList(
        pageNumber: Int?,
    ): Result<List<Quote>> {
        return try {
            Result.success(quoteService.quoteList(null, null, pageNumber).quotes.map(::quote))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
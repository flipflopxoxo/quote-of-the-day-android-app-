package com.clydelizardo.quotes.repository

import com.clydelizardo.quotes.repository.model.Quote

interface QuoteRepository {
    suspend fun getQuoteOfTheDay(): Result<Quote>

    suspend fun getQuoteList(pageNumber: Int? = null, quoteListFilter: QuoteListFilter? = null): Result<List<Quote>>
}

data class QuoteListFilter(
    val filterBy: FilterField,
    val lookup: String
)

enum class FilterField {
    TAG, AUTHOR, USER
}
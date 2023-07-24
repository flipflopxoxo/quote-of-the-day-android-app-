package com.clydelizardo.quotes.database

import com.clydelizardo.quotes.database.model.Tag
import com.clydelizardo.quotes.repository.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.clydelizardo.quotes.database.model.Quote as DbQuote

class SavedQuoteRepositoryImpl @Inject constructor(
    private val quoteDao: QuoteDao,
) : SavedQuoteRepository {
    override fun quotes(): Flow<List<Quote>> {
        return quoteDao.observableQuoteWithTag().map {
            it.map { quoteWithTag ->
                val quote = quoteWithTag.quote
                Quote(
                    id = quote.id,
                    content = quote.content,
                    author = quote.author,
                    tags = quoteWithTag.tags.filter { tag ->
                        tag.quoteId == quote.id
                    }.map { tag ->
                        tag.tag
                    }.toSet()
                )
            }
        }
    }

    override suspend fun saveQuote(quote: Quote) {
        quoteDao.saveQuoteWithTag(
            toDbQuote(quote),
            getDbTags(quote)
        )
    }

    private fun getDbTags(quote: Quote) = quote.tags.map {
        Tag(
            quoteId = quote.id,
            tag = it
        )
    }

    private fun toDbQuote(quote: Quote) = DbQuote(
        id = quote.id,
        author = quote.author,
        content = quote.content
    )

    override suspend fun deleteQuote(quote: Quote) {
        quoteDao.deleteQuoteWithTag(
            toDbQuote(quote),
            getDbTags(quote)
        )
    }

    override suspend fun deleteQuotes(quoteList: List<Quote>) {
        val dbQuoteList = quoteList.map(::toDbQuote)
        val dbTagList = quoteList.flatMap(::getDbTags)
        quoteDao.deleteQuoteAndTagList(dbQuoteList, dbTagList)
    }
}
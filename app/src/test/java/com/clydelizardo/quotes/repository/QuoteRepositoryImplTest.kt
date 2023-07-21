package com.clydelizardo.quotes.repository

import com.clydelizardo.quotes.api.QuoteService
import com.clydelizardo.quotes.api.model.QuoteListResponse
import com.clydelizardo.quotes.api.model.QuoteOfTheDayResponse
import com.clydelizardo.quotes.repository.model.Quote
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import com.clydelizardo.quotes.api.model.Quote as ApiQuote

class QuoteRepositoryImplTest {
    val testApiQuote = ApiQuote(
        id = 0,
        dialogue = false,
        private = false,
        tags = emptyList(),
        url = "http://google.com",
        favoritesCount = 0,
        upvotesCount = 0,
        downvotesCount = 0,
        author = "me",
        authorPermalink = "http://google.com",
        body = "Hello world"
    )
    val testRepositoryQuote = Quote(
        id = 0,
        content = "Hello world",
        author = "me",
        tags = emptySet()
    )

    lateinit var service: QuoteService
    lateinit var quoteRepositoryImpl: QuoteRepositoryImpl

    @Before
    fun setUp() {
        service = mockk()
        quoteRepositoryImpl = QuoteRepositoryImpl(service)
    }

    @Test()
    fun givenQotdFails_resultReturnsFailedResult() = runTest {
        coEvery { service.quoteOfTheDay() } throws Exception()

        val quoteOfTheDay = quoteRepositoryImpl.getQuoteOfTheDay()
        assert(quoteOfTheDay.isFailure)
        assertNotNull(quoteOfTheDay.exceptionOrNull())
    }

    @Test()
    fun givenQotdPasses_resultReturnsSuccessResult() = runTest {
        coEvery { service.quoteOfTheDay() } returns QuoteOfTheDayResponse(
            qotdDate = "",
            quote = testApiQuote
        )

        val quoteOfTheDay = quoteRepositoryImpl.getQuoteOfTheDay()
        assert(quoteOfTheDay.isSuccess)
        assertEquals(
            Quote(
                id = 0,
                content = "Hello world",
                author = "me",
                tags = emptySet()
            ),
            quoteOfTheDay.getOrNull()
        )
    }

    @Test()
    fun givenQuoteListFails_resultReturnsFailedResult() = runTest {
        coEvery { service.quoteList(any(), any(), any()) } throws Exception()

        val quoteOfTheDay = quoteRepositoryImpl.getQuoteList()
        assert(quoteOfTheDay.isFailure)
        assertNotNull(quoteOfTheDay.exceptionOrNull())
    }

    @Test()
    fun givenQuoteListPasses_resultReturnsSuccessResult() {
        runTest {
            coEvery { service.quoteList(any(), any(), any()) } returns QuoteListResponse(
                quotes = listOf(testApiQuote)
            )

            val quoteOfTheDay = quoteRepositoryImpl.getQuoteList()
            assert(quoteOfTheDay.isSuccess)
            assertEquals(listOf(testRepositoryQuote), quoteOfTheDay.getOrNull())
        }
    }
}
package com.clydelizardo.quotes.repository

import com.clydelizardo.quotes.api.QuoteService
import com.clydelizardo.quotes.api.model.QuoteListResponse
import com.clydelizardo.quotes.api.model.QuoteOfTheDayResponse
import com.clydelizardo.quotes.model.Quote
import com.clydelizardo.quotes.testApiQuote
import com.clydelizardo.quotes.testRepositoryQuote
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class QuoteRepositoryImplTest {
    private lateinit var service: QuoteService
    private lateinit var quoteRepositoryImpl: QuoteRepositoryImpl

    @Before
    fun setUp() {
        service = mockk()
        quoteRepositoryImpl = QuoteRepositoryImpl(service)
    }

    @Test
    fun givenQotdFails_resultReturnsFailedResult() = runTest {
        coEvery { service.quoteOfTheDay() } throws Exception()

        val quoteOfTheDay = quoteRepositoryImpl.getQuoteOfTheDay()
        assert(quoteOfTheDay.isFailure)
        assertNotNull(quoteOfTheDay.exceptionOrNull())
    }

    @Test
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

    @Test
    fun givenQuoteListFails_resultReturnsFailedResult() = runTest {
        coEvery { service.quoteList(any(), any(), any()) } throws Exception()

        val quoteOfTheDay = quoteRepositoryImpl.getQuoteList()
        assert(quoteOfTheDay.isFailure)
        assertNotNull(quoteOfTheDay.exceptionOrNull())
    }

    @Test
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
package com.clydelizardo.quotes.quoteList

import com.clydelizardo.quotes.database.SavedQuoteRepository
import com.clydelizardo.quotes.qotd.ErrorQuote
import com.clydelizardo.quotes.qotd.QuoteOfTheDayState
import com.clydelizardo.quotes.qotd.QuoteOfTheDayViewModel
import com.clydelizardo.quotes.repository.QuoteRepository
import com.clydelizardo.quotes.testRepositoryQuote
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuoteListViewModelTest {
    private lateinit var testDispatcher: CoroutineDispatcher
    private lateinit var repository: QuoteRepository
    private lateinit var savedQuoteRepository: SavedQuoteRepository
    private lateinit var viewModel: QuoteOfTheDayViewModel

    @Before
    fun setUp() {
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        savedQuoteRepository = mockk()
        every { savedQuoteRepository.quotes() } returns MutableStateFlow(emptyList())
    }

    private fun createViewModel() {
        viewModel = QuoteOfTheDayViewModel(repository, savedQuoteRepository, testDispatcher)
    }

    @Test
    fun givenQotdFetchIsOngoing_thenStateIsLoading() = runTest {
        setupLoadingQotdRequest()

        createViewModel()

        assertEquals(QuoteOfTheDayState(isLoading = true, null), viewModel.state.value)
    }


    @Test
    fun givenQotdFetchFails_thenErrorQuoteIsDisplayed() = runTest {
        setupFailedQotdResult()

        createViewModel()

        assertEquals(QuoteOfTheDayState(isLoading = false, ErrorQuote), viewModel.state.value)
    }

    @Test
    fun givenQotdFetchSucceeds_thenRetrievedQuoteIsDisplayed() = runTest {
        setupSuccessfulQotdResult()

        createViewModel()

        assertEquals(
            QuoteOfTheDayState(isLoading = false, testRepositoryQuote),
            viewModel.state.value
        )
    }

    @Test
    fun givenQotdFetchIsOngoingInRefresh_thenStateIsLoading() = runTest {
        setupSuccessfulQotdResult()
        createViewModel()
        setupLoadingQotdRequest()

        viewModel.refresh()

        assertEquals(
            QuoteOfTheDayState(isLoading = true, testRepositoryQuote),
            viewModel.state.value
        )
    }

    private fun setupLoadingQotdRequest() {
        coEvery { repository.getQuoteOfTheDay() } coAnswers {
            delay(Long.MAX_VALUE)
            throw Exception()
        }
    }


    @Test
    fun givenQotdFetchFailsInRefresh_thenErrorQuoteIsDisplayed() = runTest {
        setupSuccessfulQotdResult()
        createViewModel()
        setupFailedQotdResult()

        viewModel.refresh()

        assertEquals(QuoteOfTheDayState(isLoading = false, ErrorQuote), viewModel.state.value)
    }

    private fun setupFailedQotdResult() {
        coEvery { repository.getQuoteOfTheDay() } returns Result.failure(Exception())
    }

    @Test
    fun givenQotdFetchSucceedsInRefresh_thenRetrievedQuoteIsDisplayed() = runTest {
        setupFailedQotdResult()
        createViewModel()
        setupSuccessfulQotdResult()

        viewModel.refresh()

        assertEquals(
            QuoteOfTheDayState(isLoading = false, testRepositoryQuote),
            viewModel.state.value
        )
    }

    private fun setupSuccessfulQotdResult() {
        coEvery { repository.getQuoteOfTheDay() } returns Result.success(testRepositoryQuote)
    }

}
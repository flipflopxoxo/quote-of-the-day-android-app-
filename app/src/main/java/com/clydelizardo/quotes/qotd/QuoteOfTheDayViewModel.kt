package com.clydelizardo.quotes.qotd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clydelizardo.quotes.di.IODispatcher
import com.clydelizardo.quotes.model.Quote
import com.clydelizardo.quotes.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

val ErrorQuote = Quote(
    id = -1,
    content = "Sorry, I couldn't find a good quote.",
    author = "the phone",
    tags = emptySet()
)

@HiltViewModel
class QuoteOfTheDayViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _state = MutableStateFlow(QuoteOfTheDayState())
    val state: StateFlow<QuoteOfTheDayState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val quoteOfTheDay = withContext(ioDispatcher) {
                quoteRepository.getQuoteOfTheDay()
            }
            _state.update {
                it.copy(
                    isLoading = false,
                    quote = quoteOfTheDay.getOrNull() ?: ErrorQuote
                )
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val quoteOfTheDay = withContext(ioDispatcher) {
                quoteRepository.getQuoteOfTheDay()
            }
            _state.update {
                it.copy(
                    isLoading = false,
                    quote = quoteOfTheDay.getOrNull() ?: ErrorQuote
                )
            }
        }
    }
}

data class QuoteOfTheDayState(
    val isLoading: Boolean = true,
    val quote: Quote? = null,
)
package com.clydelizardo.quotes.qotd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clydelizardo.quotes.repository.QuoteRepository
import com.clydelizardo.quotes.repository.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

val ErrorQuote = Quote(
    -1,
    content = "Sorry, I couldn't find a good quote.",
    author = "the phone",
    tags = emptySet()
)

@HiltViewModel
class QuoteOfTheDayViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
) : ViewModel() {
    private val _state = MutableLiveData(QuoteOfTheDayState())
    val state: LiveData<QuoteOfTheDayState>
        get() = _state

    init {
        viewModelScope.launch {
            val quoteOfTheDay = quoteRepository.getQuoteOfTheDay()
            _state.value = _state.value!!.copy(
                isLoading = false,
                quote = quoteOfTheDay.getOrNull() ?: ErrorQuote
            )
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value!!.copy(
                isLoading = true
            )
            val quoteOfTheDay = quoteRepository.getQuoteOfTheDay()
            _state.value = _state.value!!.copy(
                isLoading = false,
                quote = quoteOfTheDay.getOrNull() ?: ErrorQuote
            )
        }
    }
}

data class QuoteOfTheDayState(
    val isLoading: Boolean = true,
    val quote: Quote? = null,
)
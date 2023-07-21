package com.clydelizardo.quotes.qotd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clydelizardo.quotes.repository.QuoteRepository
import com.clydelizardo.quotes.repository.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuoteOfTheDayViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
) : ViewModel() {
    private val _state = MutableLiveData(QuoteOfTheDayState())
    val state: LiveData<QuoteOfTheDayState>
        get() = _state

    init {
        viewModelScope.launch {
            val quoteOfTheDay = withContext(Dispatchers.IO) {
                quoteRepository.getQuoteOfTheDay()
            }
            _state.value = _state.value!!.copy(
                isLoading = false,
                quote = quoteOfTheDay
            )
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value!!.copy(
                isLoading = true
            )
            val quoteOfTheDay = withContext(Dispatchers.IO) {
                quoteRepository.getQuoteOfTheDay()
            }
            _state.value = _state.value!!.copy(
                isLoading = false,
                quote = quoteOfTheDay
            )
        }
    }
}

data class QuoteOfTheDayState(
    val isLoading: Boolean = true,
    val quote: Quote? = null,
)
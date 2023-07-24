package com.clydelizardo.quotes.savedQuotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clydelizardo.quotes.database.SavedQuoteRepository
import com.clydelizardo.quotes.repository.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedQuotesViewModel @Inject constructor(
    private val savedQuoteRepository: SavedQuoteRepository,
) : ViewModel() {
    val savedQuotes = savedQuoteRepository.quotes()
    private val _selectedQuotes = MutableStateFlow(emptySet<Quote>())
    val selectedQuotes = _selectedQuotes.asStateFlow()

    fun updateQuoteSavedState(quote: Quote, newSavedState: Boolean) {
        viewModelScope.launch {
            if (newSavedState) {
                savedQuoteRepository.saveQuote(quote)
            } else {
                savedQuoteRepository.deleteQuote(quote)
            }
        }
    }

    init {
        viewModelScope.launch {
            savedQuotes.collect { saveQuoteList ->
                _selectedQuotes.update { selectedQuoteSet ->
                    selectedQuoteSet.filter { quote ->
                        saveQuoteList.contains(quote)
                    }.toSet()
                }
            }
        }
    }

    fun updateQuoteSelected(quote: Quote, isSelected: Boolean) {
        if (isSelected) {
            _selectedQuotes.update {
                it + quote
            }
        } else {
            _selectedQuotes.update {
                it - quote
            }
        }
    }

    fun deleteSelectedQuotes() {
        viewModelScope.launch {
            savedQuoteRepository.deleteQuotes(_selectedQuotes.value.toList())
        }
    }
}
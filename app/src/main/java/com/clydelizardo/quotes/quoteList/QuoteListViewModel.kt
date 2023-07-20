package com.clydelizardo.quotes.quoteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.clydelizardo.quotes.repository.QuoteListFilter
import com.clydelizardo.quotes.repository.QuoteRepository
import com.clydelizardo.quotes.repository.model.Quote
import com.clydelizardo.quotes.repository.paging.QuoteListPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class QuoteListViewModel @Inject constructor(
    private val repository: QuoteRepository,
) : ViewModel() {
    private val _filter = MutableStateFlow<QuoteListFilter?>(null)
    private val _pager = _filter.flatMapLatest {
        Pager(PagingConfig(pageSize = 25)) {
            QuoteListPagingSource(repository, it)
        }.flow.cachedIn(viewModelScope)
    }

    val state = _filter.combine(_pager) { filter, pager ->
        QuoteListState(filter, pager)
    }

    fun setFilter(filter: QuoteListFilter?) {
        _filter.value = filter
    }
}

data class QuoteListState(
    val filter: QuoteListFilter? = null,
    val pagingDate: PagingData<Quote>? = null,
)
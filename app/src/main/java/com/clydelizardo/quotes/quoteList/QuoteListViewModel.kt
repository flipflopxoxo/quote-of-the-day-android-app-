package com.clydelizardo.quotes.quoteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.clydelizardo.quotes.repository.QuoteRepository
import com.clydelizardo.quotes.repository.paging.QuoteListPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteListViewModel @Inject constructor(
    private val repository: QuoteRepository,
) : ViewModel() {
    val pager = Pager(PagingConfig(pageSize = 25, prefetchDistance = 1, initialLoadSize = 1)) {
        QuoteListPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}
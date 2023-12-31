package com.clydelizardo.quotes.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.clydelizardo.quotes.model.Quote
import com.clydelizardo.quotes.repository.QuoteRepository
import javax.inject.Inject

class QuoteListPagingSource @Inject constructor(
    private val quoteRepository: QuoteRepository,
) : PagingSource<Int, Quote>() {
    override fun getRefreshKey(state: PagingState<Int, Quote>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quote> {
        val pageNumber = params.key ?: 1
        return try {
            val result = quoteRepository.getQuoteList(pageNumber)
            val quoteList = result.getOrThrow()
            LoadResult.Page(
                quoteList,
                if (pageNumber <= 1) null else pageNumber - 1,
                if (quoteList.size < 25) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
package com.clydelizardo.quotes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.clydelizardo.quotes.R
import com.clydelizardo.quotes.repository.model.Quote

@Composable
@Preview(Devices.PIXEL_3)
fun QuoteListViewPreview() {
    val quote = Quote(
        id = 0,
        content = "People assume when my hair is long, that I'm a lot cooler than I actually am. I'm not opposed to this misconception.",
        author = "Malcom Gladwell",
        tags = setOf("perception", "cool", "smart")
    )

    QuoteListItem(quote = quote)
}

@Composable
fun QuoteListView(
    lazyPagingItems: LazyPagingItems<Quote>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(lazyPagingItems.itemCount, key = {
            val id = lazyPagingItems[it]?.id
            if (id != null) " $id" else "$it "
        }) { index ->
            val quote = lazyPagingItems[index]
            if (quote != null) {
                QuoteListItem(quote = quote)
            }
        }
        val loadStates = lazyPagingItems.loadState.source
        loadStates.forEach { loadType, loadState ->
            if (loadState is LoadState.Loading) {
                item(key = Pair(loadType, loadState.javaClass)) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            } else if (loadState is LoadState.Error) {
                item(key = Pair(loadType, loadState.javaClass)) {
                    TextButton(onClick = { lazyPagingItems.retry() }) {
                        Text(text = stringResource(R.string.loading_failed_retry))
                    }
                }
            }
        }
    }
}

@Composable
fun QuoteListItem(quote: Quote) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "\"${quote.content}\"", modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "- ${quote.author}", modifier = Modifier.align(Alignment.End))
        }
    }
}
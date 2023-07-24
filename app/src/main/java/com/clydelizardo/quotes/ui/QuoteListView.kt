@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.clydelizardo.quotes.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.clydelizardo.quotes.R
import com.clydelizardo.quotes.repository.model.Quote
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

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
    savedQuotes: Collection<Quote> = emptySet(),
    onSave: (Quote, Boolean) -> Unit = { _, _ -> },
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(R.string.explore_quotes))
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(lazyPagingItems.itemCount, key = { " $it" }) { index ->
                val quote = lazyPagingItems[index]
                val isQuoteSaved = remember(quote, savedQuotes) {
                    savedQuotes.any { it.id == quote?.id }
                }
                if (quote != null) {
                    QuoteListItem(
                        modifier = Modifier.animateItemPlacement(),
                        quote = quote,
                        saveOption = QuoteOption.Save(isSaved = isQuoteSaved, onSave = onSave)
                    )
                }
            }
            val loadStates = lazyPagingItems.loadState.source
            loadStates.forEach { loadType, loadState ->
                item(key = Pair(loadType, loadState.javaClass)) {
                    if (loadState is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    } else if (loadState is LoadState.Error) {
                        TextButton(onClick = { lazyPagingItems.retry() }) {
                            Text(text = stringResource(R.string.loading_failed_retry))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuoteListItem(
    modifier: Modifier = Modifier,
    quote: Quote,
    saveOption: QuoteOption.Save? = null,
    selectOption: QuoteOption.Select? = null,
) {
    val selectOptionModifier = if (selectOption != null) {
        Modifier.selectable(selectOption.isSelected, enabled = true) {
            selectOption.onSelect(quote, !selectOption.isSelected)
        }
    } else {
        Modifier
    }
    val borderColor: Color by animateColorAsState(
        if (selectOption != null && selectOption.isSelected) {
            Color.Black
        } else {
            Color.Transparent
        }
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .then(selectOptionModifier),
        shape = CardDefaults.elevatedShape,
        border = BorderStroke(3.dp, borderColor),
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.elevatedCardColors()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "\"${quote.content}\"",
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(1f),
                    textAlign = TextAlign.Center
                )

                if (saveOption != null) {
                    SaveToggleButton(saveOption, quote)
                }
            }
            Text(
                text = "- ${quote.author}",
                modifier = Modifier.align(Alignment.End),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun SaveToggleButton(
    saveOption: QuoteOption.Save,
    quote: Quote,
) {
    val savedIcon = if (saveOption.isSaved) {
        R.drawable.baseline_bookmark_24
    } else {
        R.drawable.baseline_bookmark_border_24
    }
    IconButton(onClick = {
        saveOption.onSave(quote, !saveOption.isSaved)
    }) {
        Icon(painter = painterResource(id = savedIcon), contentDescription = null)
    }
}

sealed class QuoteOption {
    data class Save(val isSaved: Boolean, val onSave: (Quote, Boolean) -> Unit) : QuoteOption()
    data class Select(val isSelected: Boolean, val onSelect: (Quote, Boolean) -> Unit) :
        QuoteOption()
}
@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class
)

package com.clydelizardo.quotes.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clydelizardo.quotes.R
import com.clydelizardo.quotes.model.Quote

fun <S> fadeTransition(): AnimatedContentScope<S>.() -> ContentTransform = {
    fadeIn(animationSpec = tween(220, delayMillis = 90)) with
            fadeOut(animationSpec = tween(90))
}

@Composable
fun SavedQuoteListView(
    quoteList: List<Quote>,
    selectedQuotes: Set<Quote> = emptySet(),
    onSelect: (Quote, Boolean) -> Unit,
    onDelete: () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        val selectedCount = remember(selectedQuotes) {
            selectedQuotes.size
        }
        val hasSelected = remember(selectedQuotes.size) {
            selectedQuotes.isNotEmpty()
        }
        val booleanFadeTransition = remember {
            fadeTransition<Boolean>()
        }
        TopAppBar(title = {
            AnimatedContent(
                targetState = hasSelected,
                transitionSpec = booleanFadeTransition
            ) { hasSelected ->
                Text(text = if (hasSelected) "$selectedCount selected" else "Saved Quotes")
            }
        }, actions = {
            AnimatedContent(
                targetState = hasSelected,
                transitionSpec = booleanFadeTransition
            ) { showButtonState ->
                if (showButtonState) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Delete selected quotes"
                        )
                    }
                }
            }
        })
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item(key = "") {
                if (quoteList.isEmpty()) {
                    NoSavedQuotes(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .animateItemPlacement()
                    )
                }
            }
            items(count = quoteList.size, key = { index ->
                quoteList[index].id
            }, itemContent = {
                val quote = quoteList[it]
                val isSelected = remember(quote, selectedQuotes) {
                    selectedQuotes.contains(quote)
                }
                QuoteListItem(
                    modifier = Modifier.animateItemPlacement(),
                    quote = quote,
                    selectOption = QuoteOption.Select(isSelected, onSelect)
                )
            })
        }
    }
}

@Composable
fun NoSavedQuotes(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.no_saved_quotes),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}
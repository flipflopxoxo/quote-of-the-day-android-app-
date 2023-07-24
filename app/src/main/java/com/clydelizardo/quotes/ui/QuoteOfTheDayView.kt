@file:OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)

package com.clydelizardo.quotes.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clydelizardo.quotes.R
import com.clydelizardo.quotes.model.Quote
import com.clydelizardo.quotes.qotd.ErrorQuote
import com.clydelizardo.quotes.qotd.QuoteOfTheDayState

@Composable
@Preview(Devices.PIXEL_3)
fun QuoteOfTheDayPreview() {
    val quote = Quote(
        id = 0,
        content = "People assume when my hair is long, that I'm a lot cooler than I actually am. I'm not opposed to this misconception.",
        author = "Malcom Gladwell",
        tags = setOf("perception", "cool", "smart")
    )
    QuoteOfTheDayPage(quoteOfTheDayState = QuoteOfTheDayState(isLoading = false, quote = quote),
        onRefresh = { }
    ) { _, _ -> }
}

@Composable
fun QuoteOfTheDayPage(
    quoteOfTheDayState: QuoteOfTheDayState,
    onRefresh: (() -> Unit)?,
    onSave: (Quote, Boolean) -> Unit,
) {
    val quote = remember(quoteOfTheDayState) {
        quoteOfTheDayState.quote
    }
    val isLoading = remember(quoteOfTheDayState) {
        quoteOfTheDayState.isLoading
    }
    val isSaved = remember(quoteOfTheDayState) {
        quoteOfTheDayState.isSaved
    }
    val showTopBar = remember(quoteOfTheDayState) {
        !quoteOfTheDayState.isLoading && quoteOfTheDayState.quote != null
    }
    Scaffold(
        topBar = {
            AnimatedContent(targetState = showTopBar, transitionSpec = fadeTransition()) {
                if (it && quote != null) {
                    QuoteOfTheDayTopAppBar(
                        quote = quote,
                        onSave = onSave,
                        isSaved = isSaved,
                        onRefresh = onRefresh
                    )
                }
            }
        }
    ) {
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            targetState = isLoading
        ) { isLoading ->
            if (isLoading || quote == null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(128.dp)
                            .align(Alignment.Center)
                            .testTag("loading spinner")
                    )
                }
            } else {
                QuoteOfTheDayView(quote = quote)
            }
        }
    }
}

@Composable
fun QuoteOfTheDayView(
    quote: Quote,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val text = AnnotatedString(
            "\"${quote.content}\"\n",
            ParagraphStyle(TextAlign.Center)
        ) + AnnotatedString(
            "- ${quote.author}",
            SpanStyle(fontSize = MaterialTheme.typography.headlineMedium.fontSize),
            ParagraphStyle(TextAlign.End)
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp),
            text = text,
            softWrap = true,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            lineHeight = MaterialTheme.typography.headlineLarge.lineHeight * 1.2
        )
    }
}

@Composable
private fun QuoteOfTheDayTopAppBar(
    quote: Quote,
    onSave: (Quote, Boolean) -> Unit,
    isSaved: Boolean,
    onRefresh: (() -> Unit)?,
) {
    TopAppBar(title = {}, actions = {
        val showSave = remember(quote) {
            quote != ErrorQuote
        }
        AnimatedContent(targetState = showSave) { showSaveButton ->
            if (showSaveButton) {
                IconButton(onClick = {
                    onSave(quote, !isSaved)
                }) {
                    Icon(
                        painter = painterResource(
                            id = if (isSaved) {
                                R.drawable.baseline_bookmark_24
                            } else {
                                R.drawable.baseline_bookmark_border_24
                            }
                        ),
                        contentDescription = null
                    )
                }
            }
        }
        if (onRefresh != null) {
            IconButton(
                onClick = onRefresh,
                modifier = Modifier
                    .testTag("refresh button")
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_refresh_24),
                    contentDescription = "get a new quote",
                )
            }
        }
    })
}
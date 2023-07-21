package com.clydelizardo.quotes.ui

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.clydelizardo.quotes.qotd.QuoteOfTheDayState
import org.junit.Rule
import org.junit.Test

class QuoteOfTheDayViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadedQuoteState() {
        composeTestRule.setContent {
            val quoteOfTheDayState = QuoteOfTheDayState(
                isLoading = false,
                quote = com.clydelizardo.quotes.repository.model.Quote(
                    id = 0,
                    content = "Hello world",
                    author = "me",
                    tags = emptySet()
                )
            )
            QuoteOfTheDayPage(quoteOfTheDayState = quoteOfTheDayState, onRefresh = { })
        }
        composeTestRule.onNodeWithText("\"Hello world\"", substring = true).assertExists()
        composeTestRule.onNodeWithText("- me", substring = true).assertExists()
        composeTestRule.onNode(hasTestTag("refresh button")).assertExists()
        composeTestRule.onNode(hasTestTag("loading spinner")).assertDoesNotExist()
    }

    @Test
    fun loadingState() {
        composeTestRule.setContent {
            QuoteOfTheDayPage(
                quoteOfTheDayState = QuoteOfTheDayState(isLoading = true),
                onRefresh = {}
            )
        }
        composeTestRule.onNodeWithText("\"Hello world\"", substring = true).assertDoesNotExist()
        composeTestRule.onNodeWithText("- me", substring = true).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("refresh button")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("loading spinner")).assertExists()
    }
}
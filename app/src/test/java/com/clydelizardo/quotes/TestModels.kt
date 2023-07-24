package com.clydelizardo.quotes

import com.clydelizardo.quotes.api.model.Quote

val testApiQuote = Quote(
    id = 0,
    dialogue = false,
    private = false,
    tags = emptyList(),
    url = "http://google.com",
    favoritesCount = 0,
    upvotesCount = 0,
    downvotesCount = 0,
    author = "me",
    authorPermalink = "http://google.com",
    body = "Hello world"
)
val testRepositoryQuote = com.clydelizardo.quotes.model.Quote(
    id = 0,
    content = "Hello world",
    author = "me",
    tags = emptySet()
)
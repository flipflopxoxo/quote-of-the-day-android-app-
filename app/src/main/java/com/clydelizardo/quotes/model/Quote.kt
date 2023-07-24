package com.clydelizardo.quotes.model

data class Quote(
    val id: Int,
    val content: String,
    val author: String,
    val tags: Set<String>
)
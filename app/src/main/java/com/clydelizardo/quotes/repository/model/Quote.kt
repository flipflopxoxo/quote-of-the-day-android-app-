package com.clydelizardo.quotes.repository.model

data class Quote(
    val id: Int,
    val content: String,
    val author: String,
    val tags: Set<String>
)
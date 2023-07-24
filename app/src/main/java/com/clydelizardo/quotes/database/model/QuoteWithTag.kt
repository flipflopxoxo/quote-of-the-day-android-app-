package com.clydelizardo.quotes.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class QuoteWithTag(
    @Embedded val quote: Quote,
    @Relation(
        parentColumn = "id",
        entityColumn = "quoteId"
    )
    val tags: List<Tag>
)
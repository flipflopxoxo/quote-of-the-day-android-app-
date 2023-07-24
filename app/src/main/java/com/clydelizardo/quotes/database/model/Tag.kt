package com.clydelizardo.quotes.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["quoteId", "tag"])
data class Tag(
    val quoteId: Int,
    val tag: String,
)
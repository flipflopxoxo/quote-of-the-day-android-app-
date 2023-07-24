package com.clydelizardo.quotes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey val id: Int,
    val author: String,
    val content: String,
)

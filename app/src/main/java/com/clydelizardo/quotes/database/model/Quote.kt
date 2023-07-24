package com.clydelizardo.quotes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Quote constructor(
    @PrimaryKey val id: Int,
    val author: String,
    val content: String,
    val saveTimestamp: Date,
)

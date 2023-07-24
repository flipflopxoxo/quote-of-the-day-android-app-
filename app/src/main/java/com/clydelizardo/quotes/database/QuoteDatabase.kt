package com.clydelizardo.quotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.clydelizardo.quotes.database.model.Quote
import com.clydelizardo.quotes.database.model.Tag

@Database(entities = [Quote::class, Tag::class], version = 1)
@TypeConverters(Converter::class)
abstract class QuoteDatabase: RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}
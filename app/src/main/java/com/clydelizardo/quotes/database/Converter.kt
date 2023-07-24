package com.clydelizardo.quotes.database

import androidx.room.TypeConverter
import java.util.Date

class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let(::Date)
    }

    @TypeConverter
    fun toTimestamp(value: Date?): Long? {
        return value?.time
    }
}
package com.clydelizardo.quotes.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QuoteDatabaseModule {
    @Singleton
    @Provides
    fun createQuoteDatabase(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(
            context,
            QuoteDatabase::class.java, "database"
        ).build()
    }

    @Singleton
    @Provides
    fun quoteDao(quoteDatabase: QuoteDatabase): QuoteDao {
        return quoteDatabase.quoteDao()
    }

    @Singleton
    @Provides
    fun savedRepository(savedRepositoryImpl: SavedQuoteRepositoryImpl): SavedQuoteRepository {
        return savedRepositoryImpl
    }
}
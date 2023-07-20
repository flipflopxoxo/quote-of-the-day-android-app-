package com.clydelizardo.quotes.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class QuoteRepositoryModule {
    @Provides
    fun quoteRepository(quoteRepositoryImpl: QuoteRepositoryImpl): QuoteRepository {
        return quoteRepositoryImpl
    }
}
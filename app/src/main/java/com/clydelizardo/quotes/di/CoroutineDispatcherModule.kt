package com.clydelizardo.quotes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatcherModule {
    @Singleton
    @Provides
    @IODispatcher
    fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    @MainDispatcher
    fun mainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}
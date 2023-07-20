package com.clydelizardo.quotes.api

import android.content.Context
import com.clydelizardo.quotes.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.Headers.Companion.toHeaders
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun quoteService(okHttpClient: OkHttpClient): QuoteService {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://favqs.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(QuoteService::class.java)
    }

    @Provides
    fun client(@ApplicationContext context: Context): OkHttpClient {
        val apiKey = context.resources.getString(R.string.api_key)
        return OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request()
                val build = request.newBuilder()
                    .header("Authorization", "Token token=\"$apiKey\"")
                    .build()
                chain.proceed(build)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }
}
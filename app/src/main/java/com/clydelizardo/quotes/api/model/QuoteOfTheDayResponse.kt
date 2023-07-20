package com.clydelizardo.quotes.api.model

import com.google.gson.annotations.SerializedName


data class QuoteOfTheDayResponse(
    @SerializedName("qotd_date") val qotdDate: String,
    @SerializedName("quote") val quote: Quote,
)
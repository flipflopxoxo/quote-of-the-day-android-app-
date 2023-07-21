package com.clydelizardo.quotes.api.model

import com.google.gson.annotations.SerializedName


data class QuoteListResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("last_page") var lastPage: Boolean? = null,
    @SerializedName("quotes") var quotes: List<Quote> = arrayListOf(),
)
package com.clydelizardo.quotes.api.model

import com.google.gson.annotations.SerializedName


data class Quote(
    @SerializedName("id") val id: Int,
    @SerializedName("dialogue") val dialogue: Boolean,
    @SerializedName("private") val private: Boolean,
    @SerializedName("tags") val tags: ArrayList<String> = arrayListOf(),
    @SerializedName("url") val url: String,
    @SerializedName("favorites_count") val favoritesCount: Int,
    @SerializedName("upvotes_count") val upvotesCount: Int,
    @SerializedName("downvotes_count") val downvotesCount: Int,
    @SerializedName("author") val author: String,
    @SerializedName("author_permalink") val authorPermalink: String,
    @SerializedName("body") val body: String,
)
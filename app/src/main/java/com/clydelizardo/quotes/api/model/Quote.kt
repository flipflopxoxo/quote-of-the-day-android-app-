package com.clydelizardo.quotes.api.model

import com.google.gson.annotations.SerializedName


data class Quote(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("dialogue") val dialogue: Boolean? = null,
    @SerializedName("private") val private: Boolean? = null,
    @SerializedName("tags") val tags: ArrayList<String> = arrayListOf(),
    @SerializedName("url") val url: String? = null,
    @SerializedName("favorites_count") val favoritesCount: Int? = null,
    @SerializedName("upvotes_count") val upvotesCount: Int? = null,
    @SerializedName("downvotes_count") val downvotesCount: Int? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("author_permalink") val authorPermalink: String? = null,
    @SerializedName("body") val body: String? = null,
)
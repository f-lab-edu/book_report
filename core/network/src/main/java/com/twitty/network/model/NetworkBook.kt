package com.twitty.network.model

import com.google.gson.annotations.SerializedName

val emptyNetworkBook = NetworkBook(
    title = "",
    link = "",
    image = "",
    author = "",
    price = "",
    publisher = "",
    pubDate = "",
    isbn = "",
    description = "",
)

data class NetworkBook(
    @SerializedName("title")
    val title: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("discount")
    val price: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("pubdate")
    val pubDate: String,
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("description")
    val description: String,
)

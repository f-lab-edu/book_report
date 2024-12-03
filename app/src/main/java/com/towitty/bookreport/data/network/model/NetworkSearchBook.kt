package com.towitty.bookreport.data.network.model

import com.google.gson.annotations.SerializedName


data class NetworkSearchBook(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    @SerializedName("items")
    val bookList:List<NetworkBook>
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

val emptyNetworkSearchBook = NetworkSearchBook(
    lastBuildDate = "",
    total = 0,
    start = 0,
    display = 0,
    bookList = emptyList()
)

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

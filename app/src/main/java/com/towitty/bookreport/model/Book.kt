package com.towitty.bookreport.model

import com.google.gson.annotations.SerializedName


data class Book(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    @SerializedName("items")
    val bookList:List<BookItem>
)

data class BookItem(
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

val emptyBook = Book(
    lastBuildDate = "",
    total = 0,
    start = 0,
    display = 0,
    bookList = emptyList()
)

val emptyBookItem = BookItem(
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

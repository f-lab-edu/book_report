package com.towitty.bookreport.data.network.model

import com.google.gson.annotations.SerializedName
import com.towitty.bookreport.data.database.model.BookEntity
import com.towitty.bookreport.data.repository.model.Book

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

fun NetworkBook.asBook(isFavorite: Boolean = false) = Book(
    id = 0,
    title = title,
    author = author,
    publisher = publisher,
    pubDate = pubDate,
    isbn = isbn,
    description = description,
    image = image,
    price = price,
    link = link,
    isFavorite = isFavorite,
)

fun NetworkBook.asBookEntity(isFavorite: Boolean) = BookEntity(
    isbn = isbn,
    title = title,
    author = author,
    link = link,
    image = image,
    description = description,
    price = price,
    publisher = publisher,
    pubDate = pubDate,
    isFavorite = isFavorite,
)

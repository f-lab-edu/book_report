package com.twitty.model

import kotlinx.serialization.Serializable

val emptyBook = Book(
    id = 0,
    isbn = "0",
    title = "제목",
    author = "작가",
    image = "-",
    description = "-",
    price = "-",
    link = "-",
    pubDate = "-",
    publisher = "출판사",
    isFavorite = false
)

@Serializable
data class Book(
    val id: Int,
    val isbn: String,
    val title: String,
    val author: String,
    val image: String,
    val description: String,
    val price: String,
    val link: String,
    val pubDate: String,
    val publisher: String,
    val isFavorite: Boolean
)
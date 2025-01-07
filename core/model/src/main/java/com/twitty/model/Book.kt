package com.twitty.model

import kotlinx.serialization.Serializable

val emptyBook = Book(
    id = 0,
    isbn = "0",
    title = "제목",
    author = "작가",
    image = "-",
    description = "-",
    discount = "-",
    link = "-",
    pubdate = "-",
    publisher = "출판사",
    isFavorite = false
)

@Serializable
data class Book(
    val id: Long,
    val title: String,
    val link: String,
    val image: String,
    val author: String,
    val discount: String,
    val publisher: String,
    val pubdate: String,
    val isbn: String,
    val description: String,
    val isFavorite: Boolean = false
)
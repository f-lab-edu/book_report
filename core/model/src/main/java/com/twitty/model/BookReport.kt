package com.twitty.model

val emptyBookReport = BookReport(0, emptyBook, "", "", "", emptyList(), false)

data class BookReport(
    val id: Long,
    val book: Book,
    val title: String,
    val content: String,
    val date: String,
    val tags: List<Tag>,
    val isFavorite: Boolean,
)

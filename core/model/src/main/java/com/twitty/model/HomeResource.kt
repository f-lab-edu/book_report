package com.twitty.model

data class HomeResource(
    val recommendedBooks: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val favoriteBookReports: List<BookReport> = emptyList(),
    val bookReports: List<BookReport> = emptyList(),
)

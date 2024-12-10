package com.towitty.bookreport.data.repository.model

import com.towitty.bookreport.data.database.model.BookReportEntity

val emptyBookReport = BookReport(0, emptyBook, "", "", "", emptyList(), false)

data class BookReport(
    val id: Int,
    val book: Book,
    val title: String,
    val content: String,
    val date: String,
    val tags: List<Tag>,
    val isFavorite: Boolean,
)

fun BookReport.asEntity(): BookReportEntity {
    return BookReportEntity(
        id = id,
        title = title,
        content = content,
        date = date,
        isFavorite = isFavorite,
        bookId = book.id,
        tagIds = tags.map { it.id }
    )
}

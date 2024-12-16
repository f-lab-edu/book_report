package com.twitty.core.data.model

import com.twitty.database.model.BookReportEntity
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.Tag
import com.twitty.model.emptyBook

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

fun BookReportEntity.asBookReport(
    book: Book = emptyBook,
    tag: List<Tag> = emptyList()
): BookReport {
    return BookReport(
        id = id,
        title = title,
        content = content,
        date = date,
        isFavorite = isFavorite,
        book = book,
        tags = tag
    )
}
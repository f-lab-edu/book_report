package com.towitty.bookreport.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.Tag
import com.towitty.bookreport.data.repository.model.emptyBook

@Entity(tableName = "book_reports")
data class BookReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: String,
    val isFavorite: Boolean,
    val bookId: Int,
    val tagIds: List<Int>
)

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

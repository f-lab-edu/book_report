package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.BookDao
import com.towitty.bookreport.data.database.BookReportDao
import com.towitty.bookreport.data.database.TagDao
import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.database.model.asBook
import com.towitty.bookreport.data.database.model.asTag
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.Tag
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BookReportRepository @Inject constructor(
    private val tagDao: TagDao,
    private val bookDao: BookDao,
    private val bookReportDao: BookReportDao
) {

    suspend fun fetchBookReport(id: Int): BookReport {
        bookReportDao.getBookReport(id).first().let { bookReportEntity ->
            val book = bookDao.getBook(bookReportEntity.bookId).first().asBook()
            val tagList = mutableListOf<Tag>()

            bookReportEntity.tagIds.forEach { tagId ->
                tagDao.getTag(tagId).collect {
                    tagList.add(it.asTag())
                }
            }

            return BookReport(
                id = bookReportEntity.id,
                title = bookReportEntity.title,
                content = bookReportEntity.content,
                date = bookReportEntity.date,
                isFavorite = bookReportEntity.isFavorite,
                book = book,
                tags = tagList,
            )
        }
    }

    suspend fun saveBookReport(bookReport: BookReport) {
        val entity = BookReportEntity(
            id = bookReport.id,
            title = bookReport.title,
            content = bookReport.content,
            date = bookReport.date,
            isFavorite = bookReport.isFavorite,
            bookId = bookReport.book.id,
            tagIds = bookReport.tags.map { it.id }
        )

        bookReportDao.insertBookReport(entity)
    }

    fun addTag(bookReport: BookReport, tag: Tag) = bookReport.copy(tags = bookReport.tags + tag)

    fun removeTag(bookReport: BookReport, tagId: Int): BookReport =
        bookReport.tags.find { it.id == tagId }?.let { tag -> bookReport.copy(tags = bookReport.tags - tag) }
            ?: bookReport

    fun getAllTags() = tagDao.getAllTags()
}
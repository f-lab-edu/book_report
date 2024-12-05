package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.BookDao
import com.towitty.bookreport.data.database.BookReportDao
import com.towitty.bookreport.data.database.TagDao
import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.database.model.asBook
import com.towitty.bookreport.data.database.model.asTag
import com.towitty.bookreport.data.database.model.emptyTagEntity
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.Tag
import com.towitty.bookreport.data.repository.model.asEntity
import com.towitty.bookreport.data.repository.model.emptyBook
import com.towitty.bookreport.di.BookReportDispatchers
import com.towitty.bookreport.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class BookReportRepository @Inject constructor(
    private val tagDao: TagDao,
    private val bookDao: BookDao,
    private val bookReportDao: BookReportDao,
    @Dispatcher(BookReportDispatchers.IO)
    private val dispatcherIO: CoroutineDispatcher
) {

    suspend fun fetchBookReport(id: Int): BookReport = withContext(dispatcherIO) {
        Timber.d("fetchBookReport: $id")
        bookReportDao.getBookReport(id).let { bookReportEntity ->
            val book = bookDao.getBook(bookReportEntity.bookId)?.asBook() ?: emptyBook
            val tagList = mutableListOf<Tag>()

            bookReportEntity.tagIds.forEach { tagId ->
                tagDao.getTag(tagId)?.let { tagList.add(it.asTag()) }
            }

            if (tagList.isEmpty()) {
                tagList.add(emptyTagEntity.asTag())
            }
            return@withContext BookReport(
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

    suspend fun saveBookReport(bookReport: BookReport) = withContext(dispatcherIO) {
        val entity = BookReportEntity(
            id = bookReport.id,
            title = bookReport.title,
            content = bookReport.content,
            date = bookReport.date,
            isFavorite = bookReport.isFavorite,
            bookId = bookReport.book.id,
            tagIds = if (bookReport.tags.isNotEmpty()) {
                bookReport.tags.map { it.id }
            } else listOf(0)
        )
        saveBook(bookReport.book) // this
        bookReportDao.insertBookReport(entity) // this
    }

    private fun saveBook(book: Book) = bookDao.insertBook(book.asEntity())

    fun addTag(bookReport: BookReport, tag: Tag) = bookReport.copy(tags = bookReport.tags + tag)

    fun removeTag(bookReport: BookReport, tagId: Int): BookReport =
        bookReport.tags.find { it.id == tagId }?.let { tag -> bookReport.copy(tags = bookReport.tags - tag) }
            ?: bookReport

    fun fetchBookReports() = bookReportDao.getAllBookReports()

    fun getAllTags() = tagDao.getAllTags()
}
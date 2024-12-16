package com.twitty.core.data.repository

import com.twitty.core.data.model.asBook
import com.twitty.core.data.model.asBookReport
import com.twitty.core.data.model.asEntity
import com.twitty.core.data.model.asTag
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.Tag
import com.twitty.model.emptyBook
import com.twitty.model.emptyBookReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookReportRepository @Inject constructor(
    private val tagDao: com.twitty.database.dao.TagDao,
    private val bookDao: com.twitty.database.dao.BookDao,
    private val bookReportDao: com.twitty.database.dao.BookReportDao,
) : IBookReportRepository {

    override suspend fun fetchBookReport(id: Int): BookReport {
        val bookReportEntity = bookReportDao.fetchBookReport(id)
            .firstOrNull()
            ?: return emptyBookReport

        val (book, tagList) = convertEntitiesToBookAndTags(bookReportEntity)
        return bookReportEntity.asBookReport(book, tagList)
    }

    override suspend fun saveBookReport(bookReport: BookReport) {
        saveBook(bookReport.book)
        val bookReportEntity = bookReport.asEntity()

        bookReportDao.fetchBookReport(bookReport.id).firstOrNull()?.let {
            bookReportDao.updateBookReport(bookReportEntity)
        } ?: bookReportDao.insertBookReport(bookReportEntity)
    }

    override fun addTag(bookReport: BookReport, tag: Tag) =
        bookReport.copy(tags = bookReport.tags + tag)

    override fun removeTag(bookReport: BookReport, tagId: Int): BookReport =
        bookReport.tags.find { it.id == tagId }
            ?.let { tag -> bookReport.copy(tags = bookReport.tags - tag) }
            ?: bookReport

    override fun fetchBookReports(): Flow<List<BookReport>> =
        bookReportDao.fetchBookReports().map { bookReportEntities ->
            bookReportEntities.map {
                it.asBookReport()
            }
        }

    override fun getAllTags() = tagDao.getAllTags().map { tagEntities ->
        tagEntities.map { it.asTag() }
    }

    override fun fetchFavoriteBookReports(): Flow<List<BookReport>> =
        bookReportDao.fetchFavoriteBookReports()
            .map { entities ->
                entities.map { entity ->
                    entity.asBookReport()
                }
            }

    private suspend fun convertEntitiesToBookAndTags(
        bookReportEntity: com.twitty.database.model.BookReportEntity
    ): Pair<Book, List<Tag>> {
        val book = bookDao.fetchBookById(bookReportEntity.bookId)
            .firstOrNull()
            ?.asBook()
            ?: emptyBook

        val tagList = bookReportEntity.tagIds
            .mapNotNull { tagId ->
                tagDao.getTag(tagId)
                    .firstOrNull()
                    ?.asTag()
            }
        return Pair(book, tagList)
    }

    override suspend fun saveBook(book: Book) {
        val bookEntity = book.asEntity()
        bookDao.fetchBookByIsbn(bookEntity.isbn).firstOrNull()?.let {
            bookDao.updateBook(bookEntity)
        } ?: bookDao.insertBook(bookEntity)
    }
}
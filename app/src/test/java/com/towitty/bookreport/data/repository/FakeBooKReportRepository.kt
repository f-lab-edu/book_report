package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.FakeBookReportDao
import com.towitty.bookreport.data.database.FakeTagDao
import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.database.model.asBook
import com.towitty.bookreport.data.database.model.asBookReport
import com.towitty.bookreport.data.database.model.asTag
import com.towitty.bookreport.data.network.FakeBookDao
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.Tag
import com.towitty.bookreport.data.repository.model.asEntity
import com.towitty.bookreport.data.repository.model.emptyBook
import com.towitty.bookreport.data.repository.model.emptyBookReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeBooKReportRepository(
    val tagDao: FakeTagDao,
    val bookDao: FakeBookDao,
    val bookReportDao: FakeBookReportDao,

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

    override fun addTag(bookReport: BookReport, tag: Tag) = bookReport.copy(tags = bookReport.tags + tag)

    override fun removeTag(bookReport: BookReport, tagId: Int): BookReport =
        bookReport.tags.find { it.id == tagId }?.let { tag -> bookReport.copy(tags = bookReport.tags - tag) }
            ?: bookReport

    override fun fetchBookReports() = bookReportDao.fetchBookReports()

    override fun getAllTags() = tagDao.getAllTags()

    override fun fetchFavoriteBookReports(): Flow<List<BookReport>> = flow {
        bookReportDao.fetchFavoriteBookReports()
            .map { entities ->
                entities.map { entity ->
                    entity.asBookReport()
                }
            }
            .distinctUntilChanged()
    }

    private suspend fun convertEntitiesToBookAndTags(
        bookReportEntity: BookReportEntity
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
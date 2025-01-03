package com.twitty.core.data.repository

import com.twitty.core.data.model.asBookReport
import com.twitty.core.data.model.asEntity
import com.twitty.core.data.model.asTag
import com.twitty.database.dao.BookReportDao
import com.twitty.database.dao.TagDao
import com.twitty.database.model.BookReportEntity
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.BookSearchCriteria
import com.twitty.model.Tag
import com.twitty.model.emptyBook
import com.twitty.model.emptyBookReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookReportRepository @Inject constructor(
    private val bookRepository: IBookRepository,
    private val tagDao: TagDao,
    private val bookReportDao: BookReportDao,
) : IBookReportRepository {

    override suspend fun saveBookReport(bookReport: BookReport) {
        val newBookId = bookRepository.saveBook(bookReport.book)
        val bookReportEntity = bookReport.asEntity()

        bookReportDao.fetchBookReport(bookReport.id).firstOrNull()?.let {
            bookReportDao.updateBookReport(bookReportEntity)
        } ?: bookReportDao.insertBookReport(bookReportEntity)
    }

    override suspend fun fetchBookReport(id: Long): BookReport {
        val bookReportEntity = bookReportDao.fetchBookReport(id)
            .firstOrNull()
            ?: return emptyBookReport

        val (book, tagList) = convertEntitiesToBookAndTags(bookReportEntity)
        return bookReportEntity.asBookReport(book, tagList)
    }

    override fun fetchBookReports(): Flow<List<BookReport>> =
        bookReportDao.fetchBookReports().map { bookReportEntities ->
            bookReportEntities.map {
                it.asBookReport()
            }
        }

    override fun fetchFavoriteBookReports(): Flow<List<BookReport>> =
        bookReportDao.fetchFavoriteBookReports()
            .map { entities ->
                entities.map { entity ->
                    entity.asBookReport()
                }
            }

    override fun fetchAllTags() = tagDao.getAllTags().map { tagEntities ->
        tagEntities.map { it.asTag() }
    }

    override suspend fun searchBooks(bookSearchCriteria: BookSearchCriteria): Flow<List<Book>> =
        bookRepository.searchBooks(bookSearchCriteria)

    private suspend fun convertEntitiesToBookAndTags(
        bookReportEntity: BookReportEntity
    ): Pair<Book, List<Tag>> {
        val book = bookRepository.searchBooks(BookSearchCriteria(id = bookReportEntity.bookId))
            .firstOrNull()?.get(0) ?: emptyBook

        val tagList = bookReportEntity.tagIds
            .mapNotNull { tagId ->
                tagDao.getTag(tagId)
                    .firstOrNull()
                    ?.asTag()
            }
        return Pair(book, tagList)
    }
}
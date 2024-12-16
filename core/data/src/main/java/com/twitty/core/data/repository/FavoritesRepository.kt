package com.twitty.core.data.repository

import com.twitty.core.data.model.asBook
import com.twitty.core.data.model.asBookReport
import com.twitty.database.dao.BookDao
import com.twitty.database.dao.BookReportDao
import com.twitty.model.Book
import com.twitty.model.BookReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val bookDao: BookDao,
    private val booKReportDao: BookReportDao,
) : IFavoritesRepository {
    override fun fetchFavoriteBooks(): Flow<List<Book>> {
        return bookDao.fetchFavoriteBooks().map { bookEntities -> bookEntities.map { it.asBook() } }
    }

    override fun fetchFavoriteBookReports(): Flow<List<BookReport>> {
        return booKReportDao.fetchFavoriteBookReports().map { bookReportEntities ->
            bookReportEntities.map { it.asBookReport() }
        }
    }
}
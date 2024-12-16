package com.towitty.bookreport.data.database

import com.twitty.model.emptyBookReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBookReportDao(
    val bookReportDatabase: MutableList<com.twitty.database.model.BookReportEntity>
) : com.twitty.database.dao.BookReportDao {

    override suspend fun insertBookReport(bookReportEntity: com.twitty.database.model.BookReportEntity) {
        bookReportDatabase.add(bookReportEntity)
    }

    override suspend fun deleteBookReport(bookReportEntity: com.twitty.database.model.BookReportEntity) {
        bookReportDatabase.remove(bookReportEntity)
    }

    override suspend fun updateBookReport(bookReportEntity: com.twitty.database.model.BookReportEntity) {
        val index = bookReportDatabase.indexOfFirst { it.id == bookReportEntity.id }
        if (index >= 0) {
            bookReportDatabase[index] = bookReportEntity
        }
    }

    override suspend fun fetchBookReport(id: Int): List<com.twitty.database.model.BookReportEntity> =
        bookReportDatabase.apply {
            if(isEmpty()) emptyBookReport else filter { it.id == id }
        }

    override fun fetchBookReports(): Flow<List<com.twitty.database.model.BookReportEntity>> = flow { emit(bookReportDatabase.toList()) }

    override fun fetchFavoriteBookReports(): Flow<List<com.twitty.database.model.BookReportEntity>> {
        val favoriteBookReports = bookReportDatabase.filter { it.isFavorite }
        return flow { emit(favoriteBookReports) }
    }
}
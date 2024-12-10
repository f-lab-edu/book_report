package com.towitty.bookreport.data.database

import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.repository.model.emptyBookReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBookReportDao(
    val bookReportDatabase: MutableList<BookReportEntity>
) : BookReportDao {

    override suspend fun insertBookReport(bookReportEntity: BookReportEntity) {
        bookReportDatabase.add(bookReportEntity)
    }

    override suspend fun deleteBookReport(bookReportEntity: BookReportEntity) {
        bookReportDatabase.remove(bookReportEntity)
    }

    override suspend fun updateBookReport(bookReportEntity: BookReportEntity) {
        val index = bookReportDatabase.indexOfFirst { it.id == bookReportEntity.id }
        if (index >= 0) {
            bookReportDatabase[index] = bookReportEntity
        }
    }

    override suspend fun fetchBookReport(id: Int): List<BookReportEntity> =
        bookReportDatabase.apply {
            if(isEmpty()) emptyBookReport else filter { it.id == id }
        }

    override fun fetchBookReports(): Flow<List<BookReportEntity>> = flow { emit(bookReportDatabase.toList()) }

    override fun fetchFavoriteBookReports(): Flow<List<BookReportEntity>> {
        val favoriteBookReports = bookReportDatabase.filter { it.isFavorite }
        return flow { emit(favoriteBookReports) }
    }
}
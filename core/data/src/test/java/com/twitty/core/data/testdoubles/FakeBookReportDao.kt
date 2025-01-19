package com.twitty.core.data.testdoubles

import com.twitty.core.data.model.asEntity
import com.twitty.database.dao.BookReportDao
import com.twitty.database.model.BookReportEntity
import com.twitty.model.emptyBookReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeBookReportDao : BookReportDao {

    private val bookReportEntities = MutableStateFlow<List<BookReportEntity>>(emptyList())

    override suspend fun insertBookReport(bookReport: BookReportEntity): Long {
        bookReportEntities.update { oldValues ->
            oldValues.map {
                if (it.id == bookReport.id) {
                    return -1L
                }
            }
            oldValues + bookReport
        }
        return bookReport.id
    }

    override suspend fun deleteBookReport(bookReportEntity: BookReportEntity) {
        bookReportEntities.update { oldValues ->
            oldValues.filterNot { it.id == bookReportEntity.id }
        }
    }

    override suspend fun updateBookReport(bookReportEntity: BookReportEntity) {
        bookReportEntities.update { oldValues ->
            oldValues.filterNot { it.id == bookReportEntity.id } + bookReportEntity
        }
    }

    override fun fetchBookReport(id: Long): Flow<BookReportEntity> =
        flowOf(bookReportEntities.value.find { it.id == id }
            ?: emptyBookReport.asEntity())

    override fun fetchBookReports(): Flow<List<BookReportEntity>> = bookReportEntities


    override fun fetchFavoriteBookReports(): Flow<List<BookReportEntity>> =
        bookReportEntities.map { bookReportEntities ->
            bookReportEntities.filter { it.isFavorite }
        }
}
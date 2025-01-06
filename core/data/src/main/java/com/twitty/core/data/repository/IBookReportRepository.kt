package com.twitty.core.data.repository

import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.BookSearchCriteria
import com.twitty.model.Tag
import kotlinx.coroutines.flow.Flow

interface IBookReportRepository {
    suspend fun fetchBookReport(id: Long): Flow<BookReport>
    suspend fun saveBookReport(bookReport: BookReport): Long
    suspend fun searchBooks(bookSearchCriteria: BookSearchCriteria): Flow<List<Book>>
    suspend fun fetchBookReports(): Flow<List<BookReport>>
    fun fetchAllTags(): Flow<List<Tag>>
    fun fetchFavoriteBookReports(): Flow<List<BookReport>>
}

package com.twitty.core.data.repository

import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.Tag
import kotlinx.coroutines.flow.Flow

interface IBookReportRepository {
    suspend fun fetchBookReport(id: Int): BookReport
    suspend fun saveBookReport(bookReport: BookReport)
    suspend fun saveBook(book: Book)
    fun addTag(bookReport: BookReport, tag: Tag): BookReport
    fun removeTag(bookReport: BookReport, tagId: Int): BookReport
    fun fetchBookReports(): Flow<List<BookReport>>
    fun getAllTags(): Flow<List<Tag>>
    fun fetchFavoriteBookReports(): Flow<List<BookReport>>
}

package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.database.model.TagEntity
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.Tag
import kotlinx.coroutines.flow.Flow

interface IBookReportRepository {
    suspend fun fetchBookReport(id: Int): BookReport
    suspend fun saveBookReport(bookReport: BookReport)
    suspend fun saveBook(book: Book)
    fun addTag(bookReport: BookReport, tag: Tag): BookReport
    fun removeTag(bookReport: BookReport, tagId: Int): BookReport
    fun fetchBookReports(): Flow<List<BookReportEntity>>
    fun getAllTags(): Flow<List<TagEntity>>
    fun fetchFavoriteBookReports(): Flow<List<BookReport>>
}

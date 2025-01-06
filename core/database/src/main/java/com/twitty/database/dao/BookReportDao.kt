package com.twitty.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.twitty.database.model.BookReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookReportDao {

    @Insert
    suspend fun insertBookReport(bookReport: BookReportEntity): Long

    @Delete
    suspend fun deleteBookReport(bookReportEntity: BookReportEntity)

    @Update
    suspend fun updateBookReport(bookReportEntity: BookReportEntity)

    @Query("SELECT * FROM book_reports WHERE id = :id LIMIT 1")
    fun fetchBookReport(id: Long): Flow<BookReportEntity>

    @Query("SELECT * FROM book_reports")
    fun fetchBookReports(): Flow<List<BookReportEntity>>

    @Query("SELECT * FROM book_reports WHERE isFavorite = 1")
    fun fetchFavoriteBookReports(): Flow<List<BookReportEntity>>
}
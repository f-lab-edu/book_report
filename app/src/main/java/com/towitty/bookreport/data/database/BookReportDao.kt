package com.towitty.bookreport.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.towitty.bookreport.data.database.model.BookReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookReportDao {

    @Insert
    suspend fun insertBookReport(bookReport: BookReportEntity)

    @Delete
    fun deleteBookReport(bookReportEntity: BookReportEntity)

    @Update
    fun updateBookReport(bookReportEntity: BookReportEntity)

    @Query("SELECT * FROM book_reports WHERE id = :id")
    fun getBookReport(id: Int): Flow<BookReportEntity>
}
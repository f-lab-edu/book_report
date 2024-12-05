package com.towitty.bookreport.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.towitty.bookreport.data.database.model.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Update
    fun updateBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun fetchBookById(id: Int): BookEntity?

    @Query("SELECT * FROM books WHERE isbn = :isbn LIMIT 1")
    suspend fun fetchBookByIsbn(isbn: String): BookEntity?

    @Query("SELECT * FROM books")
    fun fetchBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE isFavorite = 1")
    fun fetchFavoriteBooks(): Flow<List<BookEntity>>

}
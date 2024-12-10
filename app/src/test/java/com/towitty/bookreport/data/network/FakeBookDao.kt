package com.towitty.bookreport.data.network

import com.towitty.bookreport.data.database.BookDao
import com.towitty.bookreport.data.database.model.BookEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBookDao(
    val bookDatabase: MutableList<BookEntity> = mutableListOf()
) : BookDao {

    override suspend fun insertBook(bookEntity: BookEntity) {
        bookDatabase.add(bookEntity)
    }

    override suspend fun deleteBook(bookEntity: BookEntity) {
        bookDatabase.remove(bookEntity)
    }

    override suspend fun updateBook(bookEntity: BookEntity) {
        bookDatabase.indexOfFirst { it.id == bookEntity.id }.let { index ->
            if (index >= 0) {
                bookDatabase[index] = bookEntity
            }
        }
    }

    override suspend fun fetchBookById(id: Int): List<BookEntity> = bookDatabase.filter { it.id == id }

    override suspend fun fetchBookByIsbn(isbn: String): List<BookEntity> = bookDatabase.filter { it.isbn == isbn }

    override fun fetchBooks(): Flow<List<BookEntity>> = flow {
        emit(bookDatabase.toList())
    }

    override fun fetchFavoriteBooks(): Flow<List<BookEntity>> = flow {
        emit(bookDatabase.filter { it.isFavorite })
    }

}

package com.towitty.bookreport.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeBookDao(
    val bookDatabase: MutableList<com.twitty.database.model.BookEntity> = mutableListOf()
) : com.twitty.database.dao.BookDao {

    override suspend fun insertBook(bookEntity: com.twitty.database.model.BookEntity) {
        bookDatabase.add(bookEntity)
    }

    override suspend fun deleteBook(bookEntity: com.twitty.database.model.BookEntity) {
        bookDatabase.remove(bookEntity)
    }

    override suspend fun updateBook(bookEntity: com.twitty.database.model.BookEntity) {
        bookDatabase.indexOfFirst { it.id == bookEntity.id }.let { index ->
            if (index >= 0) {
                bookDatabase[index] = bookEntity
            }
        }
    }

    override suspend fun fetchBookById(id: Int): List<com.twitty.database.model.BookEntity> = bookDatabase.filter { it.id == id }

    override suspend fun fetchBookByIsbn(isbn: String): List<com.twitty.database.model.BookEntity> = bookDatabase.filter { it.isbn == isbn }

    override fun fetchBooksByTitle(title: String): Flow<List<com.twitty.database.model.BookEntity>> = flowOf(
        bookDatabase.find { it.title.contains(title) }?.let { listOf(it) } ?: emptyList()
    )

    override fun fetchBooks(): Flow<List<com.twitty.database.model.BookEntity>> = flow {
        emit(bookDatabase.toList())
    }

    override fun fetchFavoriteBooks(): Flow<List<com.twitty.database.model.BookEntity>> = flow {
        emit(bookDatabase.filter { it.isFavorite })
    }

}

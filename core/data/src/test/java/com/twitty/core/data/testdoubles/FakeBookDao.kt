package com.twitty.core.data.testdoubles

import com.twitty.database.dao.BookDao
import com.twitty.database.model.BookEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeBookDao : BookDao {

    private val bookEntities = MutableStateFlow(emptyList<BookEntity>())

    override suspend fun insertBook(bookEntity: BookEntity): Long {
        bookEntities.update { oldValues ->
            oldValues.map {
                if (it.id == bookEntity.id) {
                    return -1L
                }
            }
            oldValues + bookEntity
        }
        return bookEntity.id
    }

    override suspend fun deleteBook(bookEntity: BookEntity) {
        bookEntities.update { oldValues ->
            oldValues.filter { it.id != bookEntity.id }
        }
    }

    override suspend fun updateBook(bookEntity: BookEntity) {
        bookEntities.update { oldValues ->
            oldValues.filterNot { it.id == bookEntity.id } + bookEntity
        }
    }

    override suspend fun fetchBookById(id: Long): List<BookEntity> =
        bookEntities.value.filter { it.id == id }

    override suspend fun fetchBookByIsbn(isbn: String): List<BookEntity> =
        bookEntities.value.filter { it.isbn == isbn }

    override fun fetchBooksByTitle(title: String): Flow<List<BookEntity>> =
        bookEntities.map { books ->
            books.filter { book -> book.title.contains(title) }
        }

    override fun fetchBooks(): Flow<List<BookEntity>> = bookEntities

    override fun fetchFavoriteBooks(): Flow<List<BookEntity>> =
        bookEntities.map { books ->
            books.filter { book -> book.isFavorite }
        }
}

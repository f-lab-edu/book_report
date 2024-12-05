package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.BookDao
import com.towitty.bookreport.data.database.model.BookEntity
import com.towitty.bookreport.data.database.model.asBook
import com.towitty.bookreport.data.repository.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class BookLocalRepository @Inject constructor(
    private val bookDao: BookDao
) {
    fun fetchBooks(): Flow<List<BookEntity>> = bookDao.fetchBooks()

    fun fetchFavoriteBooks(): Flow<List<Book>> = bookDao.fetchFavoriteBooks().map { bookEntities ->
        bookEntities.map { it.asBook() }
    }
}
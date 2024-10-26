package com.towitty.bookreport.data.network

import com.towitty.bookreport.model.Book
import com.towitty.bookreport.model.BookItem
import com.towitty.bookreport.model.emptyBookItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRemoteRepository @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource
) {
    suspend fun searchBooks(query: String): Book {
        return bookRemoteDataSource.getBooks(query)
    }

    fun findBookByIsbn(isbn: String, bookList:List<BookItem>): BookItem {
        return bookList.find { it.isbn == isbn } ?: emptyBookItem
    }
}
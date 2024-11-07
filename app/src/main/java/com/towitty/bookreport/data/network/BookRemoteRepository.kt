package com.towitty.bookreport.data.network

import com.towitty.bookreport.model.BookItem
import com.towitty.bookreport.model.emptyBookItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRemoteRepository @Inject constructor(
    private val bookRemoteDataSource: IBookDataSource
) {
    private var bookList: List<BookItem> = emptyList()

    suspend fun searchBooks(query: String): List<BookItem> {
        bookList = bookRemoteDataSource.getBook(query).bookList
        return bookList
    }

    fun findBookByIsbn(isbn: String): BookItem = bookList.find { it.isbn == isbn } ?: emptyBookItem
}
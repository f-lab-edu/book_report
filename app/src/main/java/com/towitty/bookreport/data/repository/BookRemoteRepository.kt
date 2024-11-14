package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.network.IBookDataSource
import com.towitty.bookreport.data.network.model.BookItem
import com.towitty.bookreport.data.network.model.emptyBookItem
import javax.inject.Inject

class BookRemoteRepository @Inject constructor(
    private val bookRemoteDataSource: IBookDataSource
) : IBookRepository {
    private var bookList: List<BookItem> = emptyList()

    override suspend fun searchBooks(query: String): List<BookItem> {
        bookList = bookRemoteDataSource.getBook(query).bookList
        return bookList
    }

    override fun findBookByIsbn(isbn: String): BookItem = bookList.find { it.isbn == isbn } ?: emptyBookItem
}
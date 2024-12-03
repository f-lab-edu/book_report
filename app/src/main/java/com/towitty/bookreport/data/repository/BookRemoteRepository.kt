package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.network.IBookDataSource
import com.towitty.bookreport.data.network.model.NetworkBook
import com.towitty.bookreport.data.network.model.emptyNetworkBook
import javax.inject.Inject

class BookRemoteRepository @Inject constructor(
    private val bookRemoteDataSource: IBookDataSource
) : IBookRepository {
    private var bookList: List<NetworkBook> = emptyList()

    override suspend fun searchBooks(query: String): List<NetworkBook> {
        bookList = bookRemoteDataSource.getNetworkSearchBook(query).bookList
        return bookList
    }

    override fun findBookByIsbn(isbn: String): NetworkBook = bookList.find { it.isbn == isbn } ?: emptyNetworkBook
}
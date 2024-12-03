package com.towitty.bookreport.data.network

import com.towitty.bookreport.data.network.model.NetworkBook
import com.towitty.bookreport.data.network.model.emptyNetworkBook
import com.towitty.bookreport.data.repository.IBookRepository

class FakeBookRemoteRepository(
    val books: MutableList<NetworkBook>
) : IBookRepository {
    private val bookList = mutableListOf<NetworkBook>()

    override suspend fun searchBooks(query: String): List<NetworkBook> {
        bookList.addAll(books.filter { it.title.contains(query) })
        return bookList
    }

    override fun findBookByIsbn(isbn: String): NetworkBook = bookList.firstOrNull { it.isbn == isbn } ?: emptyNetworkBook
}
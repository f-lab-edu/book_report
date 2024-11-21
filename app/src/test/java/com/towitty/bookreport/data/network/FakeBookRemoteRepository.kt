package com.towitty.bookreport.data.network

import com.towitty.bookreport.data.network.model.BookItem
import com.towitty.bookreport.data.network.model.emptyBookItem
import com.towitty.bookreport.data.repository.IBookRepository

class FakeBookRemoteRepository(
    val books: MutableList<BookItem>
) : IBookRepository {
    private val bookList = mutableListOf<BookItem>()

    override suspend fun searchBooks(query: String): List<BookItem> {
        bookList.addAll(books.filter { it.title.contains(query) })
        return bookList
    }

    override fun findBookByIsbn(isbn: String): BookItem = bookList.firstOrNull { it.isbn == isbn } ?: emptyBookItem
}
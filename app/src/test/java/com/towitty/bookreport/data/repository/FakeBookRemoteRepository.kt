package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.emptyBook

class FakeBookRemoteRepository(
    val books: MutableList<Book>
) : IBookRepository {
    private val bookList = mutableListOf<Book>()

    override suspend fun searchBooks(query: String): List<Book> {
        bookList.addAll(books.filter { it.title.contains(query) })
        return bookList
    }

    override suspend fun findBookByIsbn(isbn: String): Book = bookList.firstOrNull { it.isbn == isbn } ?: emptyBook
}
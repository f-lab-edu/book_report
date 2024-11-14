package com.towitty.bookreport.data.network

import com.towitty.bookreport.data.network.model.Book
import com.towitty.bookreport.data.network.model.emptyBook

class FakeBookRemoteDataSource : IBookDataSource {

    var book = Book(
        lastBuildDate = "",
        total = 0,
        start = 0,
        display = 0,
        bookList = mutableListOf()
    )

    override suspend fun getBook(query: String): Book {
        return book.bookList.find { it.title.contains(query) }?.let {
            book
        } ?: emptyBook
    }

}

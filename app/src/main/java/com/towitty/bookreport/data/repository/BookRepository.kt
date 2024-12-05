package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.BookDao
import com.towitty.bookreport.data.database.model.asBook
import com.towitty.bookreport.data.network.IBookDataSource
import com.towitty.bookreport.data.network.model.asBook
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.emptyBook
import com.towitty.bookreport.di.BookReportDispatchers
import com.towitty.bookreport.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookRemoteDataSource: IBookDataSource,
    private val bookDao: BookDao,
    @Dispatcher(BookReportDispatchers.IO)
    private val dispatcherIo: CoroutineDispatcher
) : IBookRepository {
    private var bookList: List<Book> = emptyList()

    override suspend fun searchBooks(query: String): List<Book> {
        bookList = bookRemoteDataSource.getNetworkSearchBook(query).bookList.map { it.asBook() }
        return bookList
    }

    override suspend fun findBookByIsbn(isbn: String): Book = withContext(dispatcherIo) {
        return@withContext bookList.find { it.isbn == isbn } ?: bookDao.fetchBookByIsbn(isbn)?.asBook() ?: emptyBook
    }
}
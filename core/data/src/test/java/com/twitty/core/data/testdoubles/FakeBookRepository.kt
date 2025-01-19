package com.twitty.core.data.testdoubles

import com.twitty.core.data.model.asBook
import com.twitty.core.data.model.asEntity
import com.twitty.core.data.repository.IBookRepository
import com.twitty.database.dao.BookDao
import com.twitty.model.Book
import com.twitty.model.BookSearchCriteria
import com.twitty.network.retrofit.IBookDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class FakeBookRepository(
    private val bookDao: BookDao,
    private val bookRemoteDataSource: IBookDataSource
) : IBookRepository {

    override suspend fun saveBook(book: Book): Long {
        if (bookDao.fetchBookByIsbn(book.isbn).isEmpty()) {
            bookDao.insertBook(book.asEntity())
        } else {
            bookDao.updateBook(book.asEntity())
            book.id
        }
        return book.id
    }

    override suspend fun searchBooks(bookSearchCriteria: BookSearchCriteria): Flow<List<Book>> =
        when {
            bookSearchCriteria.title != null -> {
                searchByTitle(bookSearchCriteria.title!!)
            }

            bookSearchCriteria.isbn != null -> {
                searchByIsbn(bookSearchCriteria.isbn!!)
            }

            bookSearchCriteria.id != null -> {
                searchById(bookSearchCriteria.id!!)
            }

            else -> {
                Timber.tag("BookRepository").e("Invalid search criteria")
                emptyFlow()
            }
        }

    private suspend fun searchByTitle(title: String): Flow<List<Book>> = flow {
        emit(bookRemoteDataSource.fetchNetworkSearchBook(title)
            .bookList
            .map { it.asBook() })
    }


    private fun searchByIsbn(isbn: String): Flow<List<Book>> = flow {
        bookDao.fetchBookByIsbn(isbn).firstOrNull()?.let {
            emit(listOf(it.asBook()))
            return@flow
        }
        emit(bookRemoteDataSource.fetchNetworkSearchBookByIsbn(isbn)
            .bookList
            .map { it.asBook() })
    }

    private fun searchById(id: Long): Flow<List<Book>> = flow {
        emit(bookDao.fetchBookById(id).map { it.asBook() })
    }
}
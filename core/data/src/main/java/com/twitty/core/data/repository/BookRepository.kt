package com.twitty.core.data.repository

import com.twitty.core.data.model.asBook
import com.twitty.core.data.model.asEntity
import com.twitty.database.dao.BookDao
import com.twitty.model.Book
import com.twitty.model.BookSearchCriteria
import com.twitty.network.retrofit.IBookDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookRemoteDataSource: IBookDataSource,
    private val bookLocalDataSource: BookDao,
) : IBookRepository {

    override suspend fun searchBooks(bookSearchCriteria: BookSearchCriteria): Flow<List<Book>> =
        when {
            bookSearchCriteria.title != null -> {
                Timber.tag("BookRepository").d("Title: ${bookSearchCriteria.title}")
                searchByTitle(bookSearchCriteria.title!!)
            }

            bookSearchCriteria.isbn != null -> {
                Timber.tag("BookRepository").d("isbn: ${bookSearchCriteria.isbn}")
                searchByIsbn(bookSearchCriteria.isbn!!)
            }

            bookSearchCriteria.id != null -> {
                Timber.tag("BookRepository").d("id: ${bookSearchCriteria.id}")
                searchById(bookSearchCriteria.id!!)
            }

            else -> {
                Timber.tag("BookRepository").e("Invalid search criteria")
                emptyFlow()
            }
        }

    override suspend fun saveBook(book: Book): Long =
        if (bookLocalDataSource.fetchBookByIsbn(book.isbn).isEmpty()) {
            bookLocalDataSource.insertBook(book.asEntity())
        } else {
            bookLocalDataSource.updateBook(book.asEntity())
            book.id
        }


    private suspend fun searchByTitle(title: String): Flow<List<Book>> = flow {
        emit(bookRemoteDataSource.fetchNetworkSearchBook(title)
            .bookList
            .map { it.asBook() })
    }


    private fun searchByIsbn(isbn: String): Flow<List<Book>> = flow {
        bookLocalDataSource.fetchBookByIsbn(isbn).firstOrNull()?.let {
            emit(listOf(it.asBook()))
            return@flow
        }
        emit(bookRemoteDataSource.fetchNetworkSearchBookByIsbn(isbn)
            .bookList
            .map { it.asBook() })
    }

    private fun searchById(id: Long): Flow<List<Book>> = flow {

        emit(bookLocalDataSource.fetchBookById(id)
            .map {
                Timber.d("favorite: ${it.asBook().isFavorite}")
                it.asBook()
            })
    }
}





package com.twitty.core.data.repository

import com.twitty.core.data.model.asBook
import com.twitty.model.Book
import com.twitty.model.SearchBook
import com.twitty.network.retrofit.IBookDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookRemoteDataSource: IBookDataSource,
) : IBookRepository {

    override suspend fun searchBooks(searchBook: SearchBook): Flow<List<Book>> = when {
        searchBook.title != null -> searchByTitle(searchBook.title!!)
        searchBook.isbn != null -> searchByIsbn(searchBook.isbn!!)
        else -> emptyFlow()
    }

    private suspend fun searchByTitle(title: String): Flow<List<Book>> =
        flowOf(bookRemoteDataSource.fetchNetworkSearchBook(title)
            .bookList
            .map { it.asBook() })

    private suspend fun searchByIsbn(isbn: String): Flow<List<Book>> =
        flowOf(bookRemoteDataSource.fetchNetworkSearchBookByIsbn(isbn)
            .bookList
            .map { it.asBook() }
        )
}


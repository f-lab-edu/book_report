package com.towitty.bookreport.data.network

import com.towitty.bookreport.model.Book
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRemoteRepository @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource
) {
    suspend fun getSearchBook(query: String): Book {
        return bookRemoteDataSource.getBooks(query)
    }
}
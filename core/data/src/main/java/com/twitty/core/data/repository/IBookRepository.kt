package com.twitty.core.data.repository

import com.twitty.model.Book
import com.twitty.model.BookSearchCriteria
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    suspend fun searchBooks(bookSearchCriteria: BookSearchCriteria): Flow<List<Book>>
}
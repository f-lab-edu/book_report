package com.twitty.core.data.repository

import com.twitty.model.Book
import com.twitty.model.SearchBook
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    suspend fun searchBooks(searchBook: SearchBook): Flow<List<Book>>
}
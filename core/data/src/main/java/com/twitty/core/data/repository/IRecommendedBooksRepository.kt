package com.twitty.core.data.repository

import com.twitty.model.Book
import kotlinx.coroutines.flow.Flow

interface IRecommendedBooksRepository {
    fun fetchRecommendedBooks(): Flow<List<Book>>
}
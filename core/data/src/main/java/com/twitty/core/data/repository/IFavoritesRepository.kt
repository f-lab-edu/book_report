package com.twitty.core.data.repository

import com.twitty.model.Book
import com.twitty.model.BookReport
import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {
    fun fetchFavoriteBooks(): Flow<List<Book>>
    fun fetchFavoriteBookReports(): Flow<List<BookReport>>
}
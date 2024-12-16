package com.twitty.core.data.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BookLocalRepository @Inject constructor(
    private val bookDao: com.twitty.database.dao.BookDao
) {
    fun fetchBooks(): Flow<List<com.twitty.database.model.BookEntity>> = bookDao.fetchBooks()


}
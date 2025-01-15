package com.twitty.core.data.repository

import com.twitty.core.data.AssetLoader
import com.twitty.model.Book
import com.twitty.network.di.BookReportDispatchers
import com.twitty.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecommendedBooksRepository @Inject constructor(
    private val assetLoader: AssetLoader,
    @Dispatcher(BookReportDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) : IRecommendedBooksRepository {
    override fun fetchRecommendedBooks(): Flow<List<Book>> =
        flow {
            emit(assetLoader.getBooks("books.json"))
        }.flowOn(ioDispatcher)
}
package com.twitty.network.retrofit

import com.twitty.network.di.BookReportDispatchers
import com.twitty.network.di.Dispatcher
import com.twitty.network.model.NetworkBookContainer
import com.twitty.network.model.emptyNetworkBookContainer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

val TAG: String = BookRemoteDataSource::class.java.simpleName

@Singleton
class BookRemoteDataSource @Inject constructor(
    private val bookApi: ApiService,
    @Dispatcher(BookReportDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : IBookDataSource {
    override suspend fun fetchNetworkSearchBook(query: String): NetworkBookContainer {
        return withContext(ioDispatcher) {
            try {
                val response = bookApi.fetchSearchBook(query)
                if (response.isSuccessful) {
                    response.body() ?: emptyNetworkBookContainer
                } else {
                    Timber.tag(TAG).e("fail code: ${response.code()}")
                    emptyNetworkBookContainer
                }
            } catch (e: Exception) {
                Timber.tag(TAG).e("NetworkException: ${e.message}")
                emptyNetworkBookContainer
            }
        }
    }

    override suspend fun fetchNetworkSearchBookByIsbn(isbn: String): NetworkBookContainer {
        return withContext(ioDispatcher) {
            try {
                val response = bookApi.fetchDetailSearchBookByIsbn(isbn)
                if (response.isSuccessful) {
                    response.body() ?: emptyNetworkBookContainer
                } else {
                    Timber.tag(TAG).e("detail fail code: ${response.code()}")
                    emptyNetworkBookContainer
                }
            } catch (e: Exception) {
                Timber.tag(TAG).e("NetworkException: ${e.message}")
                emptyNetworkBookContainer
            }
        }
    }

}
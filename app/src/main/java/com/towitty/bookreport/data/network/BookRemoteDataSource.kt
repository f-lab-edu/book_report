package com.towitty.bookreport.data.network

import android.util.Log
import com.towitty.bookreport.data.network.model.Book
import com.towitty.bookreport.data.network.model.emptyBook
import com.towitty.bookreport.di.BookReportDispatchers
import com.towitty.bookreport.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BookRemoteDataSource @Inject constructor(
    private val bookApi: ApiService,
    @Dispatcher(BookReportDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : IBookDataSource {
    override suspend fun getBook(query: String): Book {
        return withContext(ioDispatcher) {
            try {
                val response = bookApi.getSearchBook(query)
                if (response.isSuccessful) {
                    response.body() ?: emptyBook
                } else {
                    Log.e("BookRemoteDataSource", "code: ${response.code()}")
                    emptyBook
                }
            } catch (e: Exception) {
                Log.e("BookRemoteDataSource", "NetworkException: ${e.message}")
                emptyBook
            }
        }
    }

}


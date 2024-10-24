package com.towitty.bookreport.data.network

import android.util.Log
import com.towitty.bookreport.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BookRemoteDataSource @Inject constructor(
    private val bookApi: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getBooks(query: String): Book {
        return withContext(ioDispatcher) {
            try {
                val response = bookApi.getSearchBook(query)
                if (response.isSuccessful) {
                    response.body() ?: Book(lastBuildDate = "", total = 0, start = 0, display = 0, bookList = emptyList())
                } else {
                    Log.e("BookRemoteDataSource", "code: ${response.code()}")
                    Book(lastBuildDate = "", total = 0, start = 0, display = 0, bookList = emptyList())
                }
            } catch (e: Exception) {
                Log.e("BookRemoteDataSource", "NetworkException: ${e.message}")
                Book(lastBuildDate = "", total = 0, start = 0, display = 0, bookList = emptyList())
            }
        }
    }

}


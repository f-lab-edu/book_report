package com.towitty.bookreport.data.network

import com.towitty.bookreport.data.network.model.Book

interface IBookDataSource {
    suspend fun getBook(query: String): Book
}

package com.towitty.bookreport.data.network

import com.towitty.bookreport.model.Book

interface IBookDataSource {
    suspend fun getBook(query: String): Book
}
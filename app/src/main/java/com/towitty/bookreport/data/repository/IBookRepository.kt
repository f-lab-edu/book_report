package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.network.model.BookItem

interface IBookRepository {
    suspend fun searchBooks(query: String): List<BookItem>
    fun findBookByIsbn(isbn: String): BookItem
}
package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.repository.model.Book

interface IBookRepository {
    suspend fun searchBooks(query: String): List<Book>
    suspend fun findBookByIsbn(isbn: String): Book
}
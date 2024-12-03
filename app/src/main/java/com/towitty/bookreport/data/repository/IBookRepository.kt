package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.network.model.NetworkBook

interface IBookRepository {
    suspend fun searchBooks(query: String): List<NetworkBook>
    fun findBookByIsbn(isbn: String): NetworkBook
}
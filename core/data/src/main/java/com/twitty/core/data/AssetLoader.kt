package com.twitty.core.data

import com.twitty.model.Book

interface AssetLoader {
    fun getBooks(fileName: String): List<Book>
}
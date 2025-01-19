package com.twitty.core.data.testdoubles

import com.twitty.core.data.model.asNetworkBook
import com.twitty.model.Book
import com.twitty.network.model.NetworkBookContainer
import com.twitty.network.model.emptyNetworkBookContainer
import com.twitty.network.retrofit.IBookDataSource
import kotlinx.serialization.json.Json
import java.io.File

class FakeBookRemoteDataSource : IBookDataSource {

    // "src/main/assets/books.json"
    private val books = getJsonBookList().map { it.asNetworkBook()}

    private val networkBookContainer = NetworkBookContainer(
        lastBuildDate = "",
        total = 0,
        start = 0,
        display = 0,
        bookList = books
    )

    override suspend fun fetchNetworkSearchBook(query: String): NetworkBookContainer =
        networkBookContainer.bookList.find { it.title.contains(query) }?.let {
            networkBookContainer
        } ?: emptyNetworkBookContainer

    override suspend fun fetchNetworkSearchBookByIsbn(isbn: String): NetworkBookContainer =
        networkBookContainer.bookList.find { it.isbn == isbn }?.let {
            networkBookContainer
        } ?: emptyNetworkBookContainer

    private fun getJsonBookList(): List<Book> {
        val jsonFile = File("src/main/assets/books.json")
        val jsonString = jsonFile.readText()

        return Json.decodeFromString(jsonString)
    }
}

package com.towitty.bookreport.data.network

import com.twitty.network.model.NetworkBookContainer
import com.twitty.network.model.emptyNetworkBookContainer
import com.twitty.network.retrofit.IBookDataSource

class FakeBookRemoteDataSource : IBookDataSource {

    var networkBookContainer = NetworkBookContainer(
        lastBuildDate = "",
        total = 0,
        start = 0,
        display = 0,
        bookList = mutableListOf()
    )

    override suspend fun fetchNetworkSearchBook(query: String): NetworkBookContainer {
        return networkBookContainer.bookList.find { it.title.contains(query) }?.let {
            networkBookContainer
        } ?: emptyNetworkBookContainer
    }

    override suspend fun fetchNetworkSearchBookByIsbn(isbn: String): NetworkBookContainer {
        return networkBookContainer.bookList.find { it.isbn == isbn }?.let {
            networkBookContainer
        } ?: emptyNetworkBookContainer
    }

}

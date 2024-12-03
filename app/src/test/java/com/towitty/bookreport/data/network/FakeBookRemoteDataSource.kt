package com.towitty.bookreport.data.network

import com.towitty.bookreport.data.network.model.NetworkSearchBook
import com.towitty.bookreport.data.network.model.emptyNetworkSearchBook

class FakeBookRemoteDataSource : IBookDataSource {

    var networkSearchBook = NetworkSearchBook(
        lastBuildDate = "",
        total = 0,
        start = 0,
        display = 0,
        bookList = mutableListOf()
    )

    override suspend fun getNetworkSearchBook(query: String): NetworkSearchBook {
        return networkSearchBook.bookList.find { it.title.contains(query) }?.let {
            networkSearchBook
        } ?: emptyNetworkSearchBook
    }

}

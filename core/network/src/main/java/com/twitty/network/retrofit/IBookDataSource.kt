package com.twitty.network.retrofit

import com.twitty.network.model.NetworkBookContainer

interface IBookDataSource {
    suspend fun fetchNetworkSearchBook(query: String): NetworkBookContainer
    suspend fun fetchNetworkSearchBookByIsbn(isbn: String): NetworkBookContainer
}
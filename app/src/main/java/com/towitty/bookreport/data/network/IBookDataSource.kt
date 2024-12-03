package com.towitty.bookreport.data.network

import com.towitty.bookreport.data.network.model.NetworkSearchBook

interface IBookDataSource {
    suspend fun getNetworkSearchBook(query: String): NetworkSearchBook
}

package com.twitty.network.model

import com.google.gson.annotations.SerializedName

val emptyNetworkBookContainer = NetworkBookContainer(
    lastBuildDate = "",
    total = 0,
    start = 0,
    display = 0,
    bookList = emptyList()
)

data class NetworkBookContainer(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    @SerializedName("items")
    val bookList:List<NetworkBook>
)

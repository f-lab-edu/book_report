package com.twitty.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchBook(
    val title: String? = null,
    val isbn: String? = null,
    val id: Int? = null,
) {
    companion object {
        fun fromIsbn(isbn: String) = SearchBook(isbn = isbn)
        fun fromTitle(title: String) = SearchBook(title = title)
        fun fromId(id: Int) = SearchBook(id = id)
    }
}

package com.towitty.bookreport.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data class BookReport(
        val bookReportId: Int? = null,
        val isbn: String? = null,
    ) : Routes {
        companion object {
            fun fromBookReportId(bookReportId: Int) = BookReport(bookReportId = bookReportId)
        }
    }

    @Serializable
    data object AddTag : Routes

    @Serializable
    data class BookSearch(
        val isbn: String? = null
    ) : Routes
    @Serializable
    data class BookDetail(
        val isbn: String? = null
    ) : Routes
}

fun Routes.fromIsbn(isbn: String): Routes = when (this) {
    is Routes.BookReport -> copy(isbn = isbn)
    is Routes.BookSearch -> copy(isbn = isbn)
    is Routes.BookDetail -> copy(isbn = isbn)
    else -> this
}


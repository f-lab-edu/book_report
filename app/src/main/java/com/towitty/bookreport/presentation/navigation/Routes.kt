package com.towitty.bookreport.presentation.navigation

import com.towitty.bookreport.presentation.navigation.Routes.BOOK_REPORT
import kotlinx.serialization.Serializable

object Routes {
    const val ADD_TAG: String = "book_report_add_tag"
    const val BOOK_INFO_DETAIL: String = "book_info_detail"
    const val BOOK_SEARCH_FOR_BOOK_REPORT: String = "book_report_book_search"
    const val BOOK_REPORT = "directly_book_report"
}

sealed interface AppRoute {
    val route:String

    @Serializable
    data class BookReportRoute(
        override val route: String = BOOK_REPORT,
        val bookReportId: Int? = null,
        val isbn: String? = null,
    ): AppRoute {
        companion object {
            fun fromBookReportId(bookReportId: Int): BookReportRoute {
                return BookReportRoute(bookReportId = bookReportId)
            }
        }
    }
}


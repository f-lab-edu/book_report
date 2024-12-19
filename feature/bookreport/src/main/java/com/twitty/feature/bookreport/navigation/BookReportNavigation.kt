package com.twitty.feature.bookreport.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.twitty.feature.bookreport.BookReportScreen
import kotlinx.serialization.Serializable

@Serializable
data class BookReportRoute(val id: Int)

fun NavController.navigateToBookReport(bookReportId: Int, navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = BookReportRoute(bookReportId)) {
        navOptions()
    }

fun NavGraphBuilder.bookReportScreen(
    onNavigateUp: () -> Unit,
) {
    composable<BookReportRoute> {
        BookReportScreen(
            onNavigateUp = onNavigateUp,
        )
    }
}
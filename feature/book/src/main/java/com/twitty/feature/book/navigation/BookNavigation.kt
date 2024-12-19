package com.twitty.feature.book.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.twitty.feature.book.BookDetailScreen
import kotlinx.serialization.Serializable


@Serializable
data class BookRoute(val isbn: String)

fun NavController.navigateToBook(isbn: String, navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = BookRoute(isbn)) {
        navOptions()
    }

fun NavGraphBuilder.bookScreen(
    onNavigateUp: () -> Unit,
) {
    composable<BookRoute> {
        BookDetailScreen(
            onNavigateUp = onNavigateUp,
        )
    }
}
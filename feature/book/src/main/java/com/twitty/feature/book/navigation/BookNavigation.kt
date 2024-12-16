package com.twitty.feature.book.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.twitty.feature.book.BookDetailScreen
import com.twitty.model.SearchBook
import kotlinx.serialization.Serializable


@Serializable
data class BookRoute(val book: SearchBook)

fun NavController.navigateToBook(book: SearchBook, navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = BookRoute(book)) {
        navOptions()
    }
fun NavGraphBuilder.bookScreen() {
    composable<BookRoute> {
        BookDetailScreen()    }
}
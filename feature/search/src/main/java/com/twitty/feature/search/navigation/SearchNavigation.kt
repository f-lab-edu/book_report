package com.twitty.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twitty.feature.search.BookSearchScreen
import com.twitty.feature.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

@Serializable
data object SearchBookRoute

fun NavController.navigateToSearch(navOptions: NavOptions) {
    navigate(route = SearchRoute, navOptions)
}
fun NavController.navigateToBookSearch() {
    navigate(route = SearchBookRoute)
}

fun NavGraphBuilder.searchScreen(
    onNavigateUp: () -> Unit,
    onNavigateToBook: (String) -> Unit
) {
    composable<SearchRoute> {
        SearchScreen()
    }
    composable<SearchBookRoute> {
        BookSearchScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToBook = onNavigateToBook
        )
    }
}
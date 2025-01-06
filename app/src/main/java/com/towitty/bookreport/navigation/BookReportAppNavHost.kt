package com.towitty.bookreport.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.towitty.bookreport.ui.BookReportAppState
import com.twitty.feature.book.navigation.bookScreen
import com.twitty.feature.book.navigation.navigateToBook
import com.twitty.feature.bookreport.navigation.bookReportScreen
import com.twitty.feature.bookreport.navigation.navigateToBookReport
import com.twitty.feature.calendar.navigation.calendarScreen
import com.twitty.feature.home.navigation.HomeRoute
import com.twitty.feature.home.navigation.homeScreen
import com.twitty.feature.search.navigation.searchScreen

@Composable
fun BookReportAppNavHost(
    appState: BookReportAppState,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen(
            onNavigateToBook = navController::navigateToBook,
            onNavigateToBookReport = navController::navigateToBookReport,
            onNavigateToBarcode = { TODO() },
        )
        searchScreen(
            onNavigateUp = navController::navigateUp,
            onNavigateToBook = navController::navigateToBook,
        )
        calendarScreen()
        bookReportScreen(
            onNavigateUp = navController::navigateUp,
        )
        bookScreen(
            onNavigateUp = navController::navigateUp,
            onNavigateToBookReport = navController::navigateToBookReport,
        )
    }
}
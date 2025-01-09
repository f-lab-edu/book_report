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
import com.twitty.feature.settings.navigation.navigateToSettingsAlarm
import com.twitty.feature.settings.navigation.navigateToSettingsBackup
import com.twitty.feature.settings.navigation.navigateToSettingsFeedback
import com.twitty.feature.settings.navigation.navigateToSettingsTag
import com.twitty.feature.settings.navigation.navigateToSettingsTheme
import com.twitty.feature.settings.navigation.settingsScreen

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
        settingsScreen(
            onNavigateUp = navController::navigateUp,
            onNavigateToAlarm = navController::navigateToSettingsAlarm,
            onNavigateToTag = navController::navigateToSettingsTag,
            onNavigateToBackup = navController::navigateToSettingsBackup,
            onNavigateToTheme = navController::navigateToSettingsTheme,
            onNavigateToFeedback = navController::navigateToSettingsFeedback,
        )
    }
}
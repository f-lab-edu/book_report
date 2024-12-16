package com.towitty.bookreport.ui

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.towitty.bookreport.navigation.TopLevelDestination
import com.twitty.feature.calendar.navigation.navigateToCalendar
import com.twitty.feature.home.navigation.navigateToHome
import com.twitty.feature.search.navigation.navigateToSearch
import com.twitty.feature.settings.SettingsActivity

@Composable
fun rememberBookReportAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
): BookReportAppState {
    return remember(
        navController,
    ) {
        BookReportAppState(
            navController = navController,
            context = context,
        )
    }
}

@Stable
class BookReportAppState(
    val navController: NavHostController,
    val context: Context,
//    detailBookState: State<Book>,
//    bookListState: State<List<Book>>,
//    tagListState: State<List<Tag>>,
//    bookReportListState: State<List<BookReport>>,
//    bookReportState: State<BookReport>,
//    fetchBookReport: (Int) -> Unit,
//    onSearchBooks: (SearchBook) -> Unit,
//    onSaveBook: (Book) -> Unit,
//    onSaveBookReport: (BookReport) -> Unit,
//    onRemoveTag: (Int) -> Unit,
//    onAddSelectedTag: (Int) -> Unit,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.CALENDAR -> navController.navigateToCalendar(topLevelNavOptions)
            TopLevelDestination.SEARCH -> navController.navigateToSearch(topLevelNavOptions)
        }
    }

    fun moveSettings() {
        context.startActivity(Intent(context, SettingsActivity::class.java))
    }

}
package com.towitty.bookreport.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.towitty.bookreport.model.BookItem
import com.towitty.bookreport.ui.BookReportViewModel
import com.towitty.bookreport.ui.bookreport.BookInfoDetailScreen
import com.towitty.bookreport.ui.bookreport.BookReportScreen
import com.towitty.bookreport.ui.bookreport.BookSearchScreen
import com.towitty.bookreport.ui.calendar.CalendarScreen
import com.towitty.bookreport.ui.home.HomeScreen
import com.towitty.bookreport.ui.search.SearchScreen
import com.towitty.bookreport.ui.settings.SettingsScreen

@Composable
fun Navigation(
    viewModel: BookReportViewModel,
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    val bookList: List<BookItem> by viewModel.bookList.collectAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = BottomNavItem.HOME.name) {
            HomeScreen()
        }
        composable(route = BottomNavItem.CALENDAR.name) {
            CalendarScreen()
        }
        composable(route = BottomNavItem.SEARCH.name) {
            SearchScreen()
        }
        composable(route = BottomNavItem.SETTINGS.name) {
            SettingsScreen()
        }
        composable(route = Routes.DIRECTLY_BOOK_REPORT) {
            BookReportScreen(
                onCancel = { navController.navigateUp() },
                onSave = {/*TODO*/ }
            )
        }
        composable(route = Routes.BOOK_SEARCH_FOR_BOOK_REPORT) {
            BookSearchScreen(
                onNavigateUp = { navController.navigateUp() },
                searchBook = { viewModel.searchBooks(it) },
                bookList = bookList,
                onItemClicked = { navController.navigate(Routes.BOOK_INFO_DETAIL + "/$it") },
                title = {/*미사용*/ }
            )
        }

        composable(route = Routes.BOOK_INFO_DETAIL + "/{isbn}") {
            val isbn = it.arguments?.getString("isbn") ?: ""
            BookInfoDetailScreen(
                onNavigateUp = { navController.navigateUp() },
                onSelection = { /*TODO*/ },
                bookItem = viewModel.getBookByIsbn(isbn),
            )
        }
    }
}
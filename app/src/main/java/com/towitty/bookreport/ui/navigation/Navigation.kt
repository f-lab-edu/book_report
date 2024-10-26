package com.towitty.bookreport.ui.navigation

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.towitty.bookreport.model.BookItem
import com.towitty.bookreport.model.emptyBookItem
import com.towitty.bookreport.ui.SettingsActivity
import com.towitty.bookreport.ui.bookreport.BookInfoDetailScreen
import com.towitty.bookreport.ui.bookreport.BookReportScreen
import com.towitty.bookreport.ui.bookreport.BookSearchScreen
import com.towitty.bookreport.ui.calendar.CalendarScreen
import com.towitty.bookreport.ui.home.HomeScreen
import com.towitty.bookreport.ui.search.SearchScreen

@Composable
fun Navigation(
    bookListState: State<List<BookItem>>,
    findBookByIsbn: (String) -> BookItem,
    searchBooks: (String) -> Unit,
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    val bookList: List<BookItem> by bookListState

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

        }
        composable(route = "${Routes.DIRECTLY_BOOK_REPORT}/{isbn}") {
            val previousRoute = navController.previousBackStackEntry?.destination?.route
            val isbn = it.arguments?.getString("isbn") ?: ""
            BookReportScreen(
                onCancel = { navController.navigateUp() },
                onSave = {/*TODO*/ },
                bookItem = if (previousRoute == "${Routes.BOOK_INFO_DETAIL}/{isbn}") findBookByIsbn(isbn) else emptyBookItem
            )
        }
        composable(route = Routes.BOOK_SEARCH_FOR_BOOK_REPORT) {
            BookSearchScreen(
                onNavigateUp = { navController.navigateUp() },
                searchBook = searchBooks,
                bookList = bookList,
                onItemClicked = { isbn ->
                    navController.navigate("${Routes.BOOK_INFO_DETAIL}/$isbn")
                },
                title = {/*미사용*/ }
            )
        }

        composable(route = Routes.BOOK_INFO_DETAIL + "/{isbn}") {
            val isbn = it.arguments?.getString("isbn") ?: ""
            BookInfoDetailScreen(
                onNavigateUp = { navController.navigateUp() },
                onSelection = { navController.navigate("${Routes.DIRECTLY_BOOK_REPORT}/$isbn") },
                bookItem = findBookByIsbn(isbn)
            )
        }
    }
}
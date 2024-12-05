package com.towitty.bookreport.presentation.navigation

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.network.model.NetworkBook
import com.towitty.bookreport.data.network.model.emptyNetworkBook
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.Tag
import com.towitty.bookreport.data.repository.model.asNetworkBook
import com.towitty.bookreport.presentation.ui.SettingsActivity
import com.towitty.bookreport.presentation.ui.screens.bookreport.AddTagScreen
import com.towitty.bookreport.presentation.ui.screens.bookreport.BookInfoDetailScreen
import com.towitty.bookreport.presentation.ui.screens.bookreport.BookReportScreen
import com.towitty.bookreport.presentation.ui.screens.bookreport.BookSearchScreen
import com.towitty.bookreport.presentation.ui.screens.calendar.CalendarScreen
import com.towitty.bookreport.presentation.ui.screens.home.HomeScreen
import com.towitty.bookreport.presentation.ui.screens.search.SearchScreen

@Composable
fun Navigation(
    bookListState: State<List<NetworkBook>>,
    tagListState: State<List<Tag>>,
    bookReportListState: State<List<BookReportEntity>>,
    bookReportState: State<BookReport>,
    fetchBookReport: (Int) -> Unit,
    findBookByIsbn: (String) -> NetworkBook,
    searchBooks: (String) -> Unit,
    onSaveBookReport: (BookReport) -> Unit,
    onRemoveTag: (Int) -> Unit,
    onAddSelectedTag: (Int) -> Unit,
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    val bookList: List<NetworkBook> by bookListState

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = BottomNavItem.HOME.name) {
            HomeScreen(
                bookReportListState = bookReportListState,
                onMoveBookReport = { navController.navigate(AppRoute.BookReportRoute(bookReportId = it)) },
                onMoveSettings = { context ->
                    context.startActivity(Intent(context, SettingsActivity::class.java))
                }
            )
        }
        composable(route = BottomNavItem.CALENDAR.name) {
            CalendarScreen()
        }
        composable(route = BottomNavItem.SEARCH.name) {
            SearchScreen()
        }
        /*composable(route = "${Routes.DIRECTLY_BOOK_REPORT}/{isbn}") {
            val previousRoute = navController.previousBackStackEntry?.destination?.route
            val isbn = it.arguments?.getString("isbn") ?: ""
            BookReportScreen(
                bookReportState = bookReportState,
                onCancel = { navController.navigateUp() },
                onAddSelectedTag = onAddSelectedTag,
                onRemoveTag = onRemoveTag,
                onSaveBookReport = onSaveBookReport,
                onNavigateAddTag = { navController.navigate(Routes.ADD_TAG) },
                book = if (previousRoute == "${Routes.BOOK_INFO_DETAIL}/{isbn}") findBookByIsbn(isbn) else emptyNetworkBook
            )
        }*/
        composable<AppRoute.BookReportRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<AppRoute.BookReportRoute>()
            BookReportScreen(
                bookReportState = bookReportState,
                onCancel = { navController.navigateUp() },
                onAddSelectedTag = onAddSelectedTag,
                onRemoveTag = onRemoveTag,
                onSaveBookReport = onSaveBookReport,
                onNavigateAddTag = { navController.navigate(Routes.ADD_TAG) },
                book = when {
                    route.isbn != null -> findBookByIsbn(route.isbn)
                    route.bookReportId != null -> {
                        fetchBookReport(route.bookReportId)
                        bookReportState.value.book.asNetworkBook()
                    }
                    else -> emptyNetworkBook
                }
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
                onSelection = { navController.navigate(AppRoute.BookReportRoute(isbn = isbn)) },
                networkBook = findBookByIsbn(isbn)
            )
        }

        composable(route = Routes.ADD_TAG) {
             AddTagScreen(
                 onNavigateUp = { navController.navigateUp() },
                 onAddSelectedTag = onAddSelectedTag,
                 onRemoveTag = onRemoveTag,
                 tagListState = tagListState
             )
        }
    }
}
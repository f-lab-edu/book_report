package com.towitty.bookreport.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.towitty.bookreport.R
import com.towitty.bookreport.ui.bookreport.BookReportScreen
import com.towitty.bookreport.ui.calendar.CalendarScreen
import com.towitty.bookreport.ui.home.HomeScreen
import com.towitty.bookreport.ui.search.SearchScreen
import com.towitty.bookreport.ui.settings.SettingsScreen

@Composable
fun BookReportApp() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showWritingModal by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BookReportBottomNavigation(navController = navController) },
        floatingActionButton = {
            if (currentRoute == BottomNavItem.HOME.name) {
                FloatingActionButton(
                    onClick = { showWritingModal = true },
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = stringResource(R.string.home_fab_description)
                    )
                }
            }
        }
    ) { innerPadding ->
        BookReportNavHost(
            navController = navController,
            startDestination = BottomNavItem.HOME.name,
            modifier = Modifier.padding(innerPadding)
        )

        if (showWritingModal) {
            BookReportWritingModal(
                navController = navController,
                onDismissRequest = { showWritingModal = false },
                modifier = Modifier.wrapContentHeight()

            )
        }
    }
}

@Composable
private fun BookReportNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = BottomNavItem.HOME.name) {
            HomeScreen(modifier = Modifier.fillMaxSize())
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
            BookReportScreen(onCancel = { navController.navigateUp() }, onSave = {/*TODO*/ })
        }

    }
}

@Composable
fun BookReportBottomNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(modifier = modifier) {
        BottomNavItem.entries.map {
            NavigationBarItem(
                icon = {
                    Icon(imageVector = it.icon, contentDescription = null)
                },
                label = {
                    Text(
                        text = stringResource(id = it.label)
                    )
                },
                selected = currentRoute == it.name,
                onClick = {
                    navController.navigate(it.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReportWritingModal(
    navController: NavHostController,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            BookReportModalSheetItem(
                icon = Icons.Default.Keyboard,
                label = stringResource(R.string.fab_modal_keyboard),
                onClicked = {
                    onDismissRequest()
                    navController.navigate(Routes.DIRECTLY_BOOK_REPORT)
                },
            )
            BookReportModalSheetItem(
                icon = Icons.Default.Search,
                label = stringResource(R.string.fab_modal_book_search),
                onClicked = {/*TODO*/ }
            )
            BookReportModalSheetItem(
                icon = ImageVector.vectorResource(id = R.drawable.ic_barcode_scanner),
                label = stringResource(R.string.fab_modal_barcode_scanner),
                onClicked = {/*TODO*/ }
            )
        }

    }
}

@Composable
fun BookReportModalSheetItem(icon: ImageVector, label: String, onClicked: () -> Unit) {
    TextButton(onClick = onClicked, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

enum class BottomNavItem(@StringRes val label: Int, val icon: ImageVector) {
    HOME(R.string.label_home, Icons.Default.Home),
    CALENDAR(R.string.label_calender, Icons.Default.CalendarMonth),
    SEARCH(R.string.label_search, Icons.Default.Search),
    SETTINGS(R.string.label_settings, Icons.Default.Settings)
}

@Preview(showBackground = true)
@Composable
fun BookReportWritingModalPreview() {
    BookReportWritingModal(
        navController = rememberNavController(),
        onDismissRequest = {},
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun BookReportAppPreview() {
    BookReportApp()
}
package com.towitty.bookreport.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.towitty.bookreport.R
import com.towitty.bookreport.presentation.ui.common.BookReportIcons

enum class BottomNavItem(@StringRes val label: Int, val icon: ImageVector) {
    HOME(R.string.label_home, BookReportIcons.Home),
    CALENDAR(R.string.label_calender, BookReportIcons.CalendarMonth),
    SEARCH(R.string.label_search, BookReportIcons.Search),
}

@Composable
fun AppBottomNavigation(navController: NavHostController, navBackStackEntry: NavBackStackEntry?, modifier: Modifier = Modifier) {

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
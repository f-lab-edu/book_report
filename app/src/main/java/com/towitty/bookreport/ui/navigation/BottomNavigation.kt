package com.towitty.bookreport.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.towitty.bookreport.R

enum class BottomNavItem(@StringRes val label: Int, val icon: ImageVector) {
    HOME(R.string.label_home, Icons.Default.Home),
    CALENDAR(R.string.label_calender, Icons.Default.CalendarMonth),
    SEARCH(R.string.label_search, Icons.Default.Search),
    SETTINGS(R.string.label_settings, Icons.Default.Settings)
}

@Composable
fun AppBottomNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
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
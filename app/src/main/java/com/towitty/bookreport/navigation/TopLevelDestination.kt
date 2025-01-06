package com.towitty.bookreport.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.towitty.bookreport.R
import com.twitty.designsystem.icon.BookReportIcons
import com.twitty.feature.calendar.navigation.CalendarRoute
import com.twitty.feature.home.navigation.HomeRoute
import com.twitty.feature.search.navigation.SearchRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val icon:ImageVector,
    @StringRes val label: Int,
    @StringRes val title: Int,
    val route: KClass<*>,
) {
    HOME(
        icon = BookReportIcons.Home,
        label = R.string.label_home,
        title = R.string.app_name,
        route = HomeRoute::class,
    ),
    CALENDAR(
        icon = BookReportIcons.CalendarMonth,
        label = R.string.label_calender,
        title = R.string.app_name,
        route = CalendarRoute::class,
    ),
    SEARCH(
        icon = BookReportIcons.Search,
        label = R.string.label_search,
        title = R.string.app_name,
        route = SearchRoute::class,
    ),
}

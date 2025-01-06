package com.twitty.feature.calendar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CalendarRoute // route to Calendar screen

fun NavController.navigateToCalendar(navOptions: NavOptions) =
    navigate(route = CalendarRoute, navOptions)

fun NavGraphBuilder.calendarScreen() {
    composable<CalendarRoute> {
        CalendarScreen()
    }
}
package com.twitty.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twitty.feature.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(route = HomeRoute, navOptions)

fun NavGraphBuilder.homeScreen(
    onNavigateToBookReport: (Int) -> Unit,
    onNavigateToBook: (String) -> Unit,
    onNavigateToBarcode: () -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(
            onNavigateToBookReport = onNavigateToBookReport,
            onNavigateToBook = onNavigateToBook,
            onNavigateToBarcode = onNavigateToBarcode,
        )
    }
}
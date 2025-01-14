package com.twitty.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twitty.feature.settings.SettingsMainScreen
import com.twitty.feature.settings.tag.TagManagementScreen

fun NavController.navigateToSettingsMain() {
    navigate(route = SettingsMainRoute)
}

fun NavController.navigateToSettingsAlarm() {
    navigate(route = SettingsRoute.Alarm)
}

fun NavController.navigateToSettingsTag() {
    navigate(route = SettingsRoute.Tag)
}

fun NavController.navigateToSettingsTheme() {
    navigate(route = SettingsRoute.Theme)
}

fun NavController.navigateToSettingsBackup() {
    navigate(route = SettingsRoute.Backup)
}

fun NavController.navigateToSettingsFeedback() {
    navigate(route = SettingsRoute.Feedback)
}

fun NavGraphBuilder.settingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToAlarm: () -> Unit,
    onNavigateToTag: () -> Unit,
    onNavigateToBackup: () -> Unit,
    onNavigateToTheme: () -> Unit,
    onNavigateToFeedback: () -> Unit,
) {
    composable<SettingsMainRoute> {
        SettingsMainScreen(
            onNavigateToAlarm,
            onNavigateToTag,
            onNavigateToBackup,
            onNavigateToTheme,
            onNavigateToFeedback
        )
    }

    composable<SettingsRoute.Alarm> {

    }

    composable<SettingsRoute.Tag> {
        TagManagementScreen(
            onNavigateUp = onNavigateUp,
        )
    }

    composable<SettingsRoute.Backup> {

    }

    composable<SettingsRoute.Feedback> {

    }

    composable<SettingsRoute.Theme> {

    }
}
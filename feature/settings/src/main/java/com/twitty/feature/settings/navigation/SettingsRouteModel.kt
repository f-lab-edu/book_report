package com.twitty.feature.settings.navigation

import kotlinx.serialization.Serializable

@Serializable
object SettingsMainRoute

@Serializable
sealed interface SettingsRoute {

    @Serializable
    object Alarm : SettingsRoute

    @Serializable
    object Tag : SettingsRoute

    @Serializable
    object Theme : SettingsRoute

    @Serializable
    object Backup : SettingsRoute

    @Serializable
    object Feedback : SettingsRoute

}

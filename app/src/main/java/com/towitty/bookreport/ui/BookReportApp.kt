package com.towitty.bookreport.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.towitty.bookreport.R
import com.towitty.bookreport.navigation.BookReportAppNavHost
import com.towitty.bookreport.navigation.TopLevelDestination
import com.twitty.core.ui.FabModal
import com.twitty.designsystem.component.BookReportAppTopAppBar
import com.twitty.designsystem.icon.BookReportIcons
import com.twitty.feature.bookreport.navigation.navigateToBookReport
import com.twitty.feature.search.navigation.navigateToBookSearch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReportApp(
    appState: BookReportAppState,
) {
    val currentTopLevelDestination = appState.currentTopLevelDestination
    var isShowFabModal by remember { mutableStateOf(false) }
    val isHomeDestination = currentTopLevelDestination == TopLevelDestination.HOME

    Scaffold(
        topBar = {
            if(currentTopLevelDestination != null) {
                BookReportAppTopAppBar(
                    titleRes = currentTopLevelDestination.title,
                    actionIcon = BookReportIcons.Settings,
                    actionIconContentDescription = stringResource(id = R.string.label_settings),
                    onActionClick = appState::moveSettings,
                )
            }
        },
        bottomBar = { BookReportAppNavigationBar(appState = appState) },
        floatingActionButton = {
            if (isHomeDestination) {
                FloatingActionButton(
                    onClick = { isShowFabModal = true },
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = BookReportIcons.Book,
                        contentDescription = stringResource(R.string.description_home_fab)
                    )
                }
            }
        }
    ) { innerPadding ->
        BookReportAppNavHost(
            appState = appState,
            modifier = Modifier.padding(innerPadding),
        )
        if (isShowFabModal) {
            FabModal(
                onNavigateToBookSearch = appState.navController::navigateToBookSearch,
                onNavigateToBookReport = { appState.navController.navigateToBookReport(0) },
                onNavigateToBarcode = { TODO() },
                onDismissRequest = { isShowFabModal = false },
                modifier = Modifier.wrapContentHeight()
            )
        }
    }
}

@Composable
fun BookReportAppNavigationBar(
    appState: BookReportAppState,
) {
    val currentTopLevelDestination = appState.currentTopLevelDestination
    if (currentTopLevelDestination != null) {
        NavigationBar {
            appState.topLevelDestinations.forEach { destination ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(id = destination.label)
                        )
                    },
                    label = { Text(stringResource(id = destination.label)) },
                    selected = currentTopLevelDestination == destination,
                    onClick = { appState.navigateToTopLevelDestination(destination) },
                )
            }
        }
    }
}


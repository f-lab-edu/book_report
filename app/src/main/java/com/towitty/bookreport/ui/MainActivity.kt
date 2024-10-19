package com.towitty.bookreport.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.towitty.bookreport.R
import com.towitty.bookreport.ui.components.FabModal
import com.towitty.bookreport.ui.navigation.AppBottomNavigation
import com.towitty.bookreport.ui.navigation.BottomNavItem
import com.towitty.bookreport.ui.navigation.Navigation
import com.towitty.bookreport.ui.theme.BookReportTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookReportTheme {
                BookReportApp()
            }
        }
    }
}

@Composable
fun BookReportApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var isShowFabModal by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { AppBottomNavigation(navController = navController) },
        floatingActionButton = {
            if (currentRoute == BottomNavItem.HOME.name) {
                FloatingActionButton(
                    onClick = { isShowFabModal = true },
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
        Navigation(
            navController = navController,
            startDestination = BottomNavItem.HOME.name,
            modifier = Modifier.padding(innerPadding)
        )
        if (isShowFabModal) {
            FabModal(
                onClicked = { navController.navigate(it) },
                onDismissRequest = { isShowFabModal = false },
                modifier = Modifier.wrapContentHeight()

            )
        }
    }
}


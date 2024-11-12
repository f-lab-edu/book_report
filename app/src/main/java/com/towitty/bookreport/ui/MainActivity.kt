package com.towitty.bookreport.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.towitty.bookreport.R
import com.towitty.bookreport.data.database.model.TagEntity
import com.towitty.bookreport.data.network.model.BookItem
import com.towitty.bookreport.ui.components.FabModal
import com.towitty.bookreport.ui.navigation.AppBottomNavigation
import com.towitty.bookreport.ui.navigation.BottomNavItem
import com.towitty.bookreport.ui.navigation.Navigation
import com.towitty.bookreport.ui.theme.BookReportTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: BookReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookReportTheme {
                BookReportApp(
                    bookListState = viewModel.bookList.collectAsState(),
                    addedTagListState = viewModel.addedTagList.collectAsState(),
                    tagListState = viewModel.tagList.collectAsState(),
                    searchBooks = viewModel::searchBooks,
                    onSaveBookReport = { /*TODO*/ },
                    onRemoveTag = viewModel::removeAddedTag,
                    onAddSelectTag = viewModel::addSelectedTag,
                    findBookByIsbn = viewModel::findBookByIsbn,
                )
            }
        }
    }
}

@Composable
fun BookReportApp(
    bookListState: State<List<BookItem>>,
    addedTagListState: State<List<TagEntity>>,
    tagListState: State<List<TagEntity>>,
    searchBooks: (String) -> Unit,
    onSaveBookReport: () -> Unit,
    onRemoveTag: (Int) -> Unit,
    findBookByIsbn: (String) -> BookItem,
    onAddSelectTag: (Int) -> Unit,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var isShowFabModal by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavigation(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )
        },
        floatingActionButton = {
            if (currentRoute == BottomNavItem.HOME.name) {
                FloatingActionButton(
                    onClick = { isShowFabModal = true },
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = stringResource(R.string.description_home_fab)
                    )
                }
            }
        }
    ) { innerPadding ->
        Navigation(
            bookListState = bookListState,
            addedTagListState = addedTagListState,
            tagListState = tagListState,
            findBookByIsbn = findBookByIsbn,
            searchBooks = searchBooks,
            onSaveBookReport = onSaveBookReport,
            onRemoveTag = onRemoveTag,
            onAddSelectTag = onAddSelectTag,
            navController = navController,
            startDestination = BottomNavItem.HOME.name,
            modifier = Modifier.padding(innerPadding),
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


package com.towitty.bookreport.presentation.ui.screens.bookreport

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.towitty.bookreport.R
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.presentation.ui.common.BookReportIcons
import com.towitty.bookreport.presentation.ui.components.BookCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchScreen(
    onNavigateUp: () -> Unit,
    searchBook: (String) -> Unit,
    onItemClicked: (String) -> Unit,
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
) {
    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = title,
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = BookReportIcons.ArrowBackIosNew, contentDescription = stringResource(
                                R.string.description_go_back
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BookSearchBar(
                searchText,
                { searchText = it },
                onSearch = { searchBook(searchText) },
            )
            SearchFilter(
                selectedFilter,
                { selectedFilter = if (selectedFilter == it) "" else it }
            )
            Button(onClick = {}) {
                Text(stringResource(R.string.btn_direct_book_registration))
            }
            BookList(
                bookList,
                onItemClicked = onItemClicked,
                selectedFilter = selectedFilter,
                searchText = searchText,
                Modifier.fillMaxHeight()
            )
        }
    }
}

@Composable
fun BookSearchBar(
    searchText: String,
    onValueChange: (String) -> Unit,
    onSearch: KeyboardActionScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(imageVector = BookReportIcons.Search, contentDescription = null)
        },
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = onSearch ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun SearchFilter(
    selectedFilter: String,
    onSelectedFilter: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.wrapContentHeight()
    ) {
        Icon(imageVector = BookReportIcons.FilterAlt, contentDescription = null)

        TextButton(
            onClick = { onSelectedFilter("title") },
            colors = ButtonDefaults.textButtonColors(
                containerColor = if (selectedFilter == "title") MaterialTheme.colorScheme.primary else Color.Transparent,
                contentColor = if (selectedFilter == "title") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.wrapContentSize()
        ) {
            Text(stringResource(id = R.string.str_book_title))
        }
        TextButton(
            onClick = { onSelectedFilter("author") },
            colors = ButtonDefaults.textButtonColors(
                containerColor = if (selectedFilter == "author") MaterialTheme.colorScheme.primary else Color.Transparent,
                contentColor = if (selectedFilter == "author") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.wrapContentSize()
        ) {
            Text(stringResource(id = R.string.str_author))
        }
        TextButton(
            onClick = { onSelectedFilter("publisher") },
            colors = ButtonDefaults.textButtonColors(
                containerColor = if (selectedFilter == "publisher") MaterialTheme.colorScheme.primary else Color.Transparent,
                contentColor = if (selectedFilter == "publisher") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.wrapContentSize()
        ) {
            Text(stringResource(id = R.string.str_publisher))
        }
    }
}

@Composable
fun BookList(
    bookList: List<Book>,
    onItemClicked: (String) -> Unit,
    selectedFilter: String,
    searchText: String,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(items = bookList, key = { it.isbn }) { book ->
            when (selectedFilter) {
                "title" -> if (book.title.contains(searchText))
                    BookCard(book) { onItemClicked(book.isbn) }

                "author" -> if (book.author.contains(searchText))
                    BookCard(book) { onItemClicked(book.isbn) }

                "publisher" -> if (book.publisher.contains(searchText))
                    BookCard(book) { onItemClicked(book.isbn) }

                "" -> BookCard(book) { onItemClicked(book.isbn) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookSearchBarPreview(modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun BookListPreview(modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun BookListItemPreview(modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun BookSearchScreenPreview(modifier: Modifier = Modifier) {
}

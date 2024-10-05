package com.towitty.bookreport.ui.bookreport

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.towitty.bookreport.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew, contentDescription = stringResource(
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
                .padding(innerPadding).padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BookSearchBar()
            SearchFilter()
            Button(onClick = { /*TODO*/ }) {
                Text(stringResource(R.string.btn_direct_book_registration))
            }
            BookList(Modifier.fillMaxHeight())
        }
    }
}

@Composable
fun BookSearchBar(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {/*TODO*/ },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun SearchFilter(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.wrapContentHeight()) {
        Icon(imageVector = Icons.Default.FilterAlt, contentDescription = null)
        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
            Text(stringResource(id = R.string.str_book_title))
        }
        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
            Text(stringResource(id = R.string.str_author))
        }
        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
            Text(stringResource(id = R.string.str_genre))
        }
        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
            Text(stringResource(id = R.string.str_publisher))
        }
    }
}

@Composable
fun BookList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(10) {
            BookListItem()
        }
    }
}

@Composable
fun BookListItem(modifier: Modifier = Modifier) {
    Card(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(CornerSize(8.dp)),
        modifier = modifier
            .fillMaxWidth()
            .height(166.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(166.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                Text(stringResource(id = R.string.str_book_title))
                Text(stringResource(id = R.string.str_author))
                Text(stringResource(id = R.string.str_genre))
                Text(stringResource(id = R.string.str_publisher))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookSearchBarPreview(modifier: Modifier = Modifier) {
    BookSearchBar()
}

@Preview(showBackground = true)
@Composable
fun BookListPreview(modifier: Modifier = Modifier) {
    BookList()
}

@Preview(showBackground = true)
@Composable
fun BookListItemPreview(modifier: Modifier = Modifier) {
    BookListItem()
}

@Preview(showBackground = true)
@Composable
fun BookSearchScreenPreview(modifier: Modifier = Modifier) {
    BookSearchScreen({})
}

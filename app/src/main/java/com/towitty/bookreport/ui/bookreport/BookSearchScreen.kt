package com.towitty.bookreport.ui.bookreport

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.towitty.bookreport.R
import com.towitty.bookreport.model.BookItem
import com.towitty.bookreport.ui.BookReportViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchScreen(
    onNavigateUp: () -> Unit,
    onItemClicked: (String) -> Unit,
    viewModel: BookReportViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
) {
    var searchText by remember { mutableStateOf("") }
    val bookList by viewModel.bookList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = title,
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
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
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BookSearchBar(
                searchText,
                { searchText = it },
                onSearch = { viewModel.getBooks(searchText) }
            )
            SearchFilter()
            Button(onClick = {}) {
                Text(stringResource(R.string.btn_direct_book_registration))
            }
            BookList(bookList, Modifier.fillMaxHeight(), onItemClicked = onItemClicked)
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
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = onSearch),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun SearchFilter(modifier: Modifier = Modifier) { // 라디오 처럼 Click 시 선택 되는 필터, 버튼의 배경색을 바꿀 예정
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
fun BookList(bookList: List<BookItem>, modifier: Modifier = Modifier, onItemClicked: (String) -> Unit) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(items = bookList, key = { it.isbn }) { bookItem ->
            BookListItem(bookItem) {
                onItemClicked(bookItem.isbn)
            }
        }
    }
}

@Composable
fun BookListItem(bookItem: BookItem, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val bitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    Glide.with(LocalContext.current).asBitmap().load(bookItem.image).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            bitmap.value = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            bitmap.value = null
        }
    })

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(CornerSize(8.dp)),
        modifier = modifier
            .fillMaxWidth()
            .height(166.dp)
    ) {
        Row {
            bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
                Image(
                    bitmap = fetchedBitmap,
                    contentDescription = null,
                    modifier = Modifier.size(166.dp)
                )
            } ?: Image(
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
                Text(bookItem.title)
                Text(bookItem.author)
                Text(bookItem.price)
                Text(bookItem.publisher)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookSearchBarPreview(modifier: Modifier = Modifier) {
//    BookSearchBar("", {}, {})
}

@Preview(showBackground = true)
@Composable
fun BookListPreview(modifier: Modifier = Modifier) {
//    BookList(listOf())
}

@Preview(showBackground = true)
@Composable
fun BookListItemPreview(modifier: Modifier = Modifier) {
//    BookListItem(BookItem("", "", "", "", "", "", "", "", ""))
}

@Preview(showBackground = true)
@Composable
fun BookSearchScreenPreview(modifier: Modifier = Modifier) {
//    BookSearchScreen({}, {}, {}, {})
}

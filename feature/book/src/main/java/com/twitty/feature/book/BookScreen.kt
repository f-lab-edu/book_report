package com.twitty.feature.book

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.twitty.designsystem.icon.BookReportIcons
import com.twitty.model.Book
import com.twitty.model.emptyBook

@Composable
fun BookDetailScreen(
    onFavoriteClick: (Book) -> Unit = {},
    onCameraClick: () -> Unit = {},
    onNavigateUp: () -> Unit = {},
    onSelection: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel()
) {
    val detailBook by viewModel.book.collectAsState()

    Scaffold(
        topBar = { BookDetailTopAppbar(onNavigateUp, onSelection, Modifier.fillMaxWidth()) },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            BookInfoImage(
                book = detailBook.firstOrNull() ?: emptyBook,
                onCameraClick = onCameraClick,
                onFavoriteClick = onFavoriteClick,
            )
            BookInfoDetail(
                book = detailBook.firstOrNull() ?: emptyBook,
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailTopAppbar(
    onBack: () -> Unit,
    onSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    BookReportIcons.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.description_go_back)
                )
            }
        },
        actions = {
            TextButton(
                onClick = onSelection,
                modifier = Modifier
                    .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            ) {
                Text(stringResource(R.string.str_selection))
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookInfoImage(
    book: Book,
    onCameraClick: () -> Unit,
    onFavoriteClick: (Book) -> Unit,
    isFavorite: Boolean = false,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(312.dp)
    ) {
        GlideImage(
            model = book.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.Center)
        )

        IconButton( //FavoriteButton
            onClick = { onFavoriteClick(book) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.TwoTone.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(R.string.description_favorite_btn),
                tint = if (isFavorite) Color.Red else Color.Black,
                modifier = Modifier.size(34.dp)
            )
        }

        IconButton( //CameraButton
            onClick = onCameraClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-48).dp)
                .size(48.dp)
                .background(Color.Gray, CircleShape)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = BookReportIcons.PhotoCamera,
                contentDescription = stringResource(R.string.description_camera_btn),
                tint = Color.White,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}

@Composable
fun BookInfoDetail(book: Book, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Text(
                text = book.author,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Text(
                text = book.price,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Text(
                text = book.publisher,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Text(
                text = book.isbn,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Text(
                text = book.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookInfoDetailPreview(modifier: Modifier = Modifier) {
    BookInfoDetail(
        book = Book(
            id = 0,
            title = "title",
            link = "link",
            image = "image",
            author = "author",
            price = "price",
            publisher = "publisher",
            pubDate = "pubDate",
            isbn = "isbn",
            description = "description"
        ), modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBookInfoImage() {
    BookInfoImage(
        book = emptyBook,
        onCameraClick = {},
        onFavoriteClick = {},
        isFavorite = false,
    )
}

@Preview(showBackground = true)
@Composable
fun BookInfoDetailScreenPreview(modifier: Modifier = Modifier) {
}

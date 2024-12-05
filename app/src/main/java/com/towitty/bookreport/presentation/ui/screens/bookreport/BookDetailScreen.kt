package com.towitty.bookreport.presentation.ui.screens.bookreport

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.towitty.bookreport.R
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.emptyBook
import com.towitty.bookreport.presentation.ui.common.BookReportIcons

@Composable
fun BookDetailScreen(
    book: Book,
    onFavoriteClick: (Book) -> Unit,
    onCameraClick: () -> Unit,
    onNavigateUp: () -> Unit,
    onSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }

    Glide.with(LocalContext.current).asBitmap().load(book.image).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            bitmap.value = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            bitmap.value = null
        }
    })

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
            bitmap.value?.asImageBitmap()?.let { bookItemImage ->
                BookInfoImage(
                    book = book,
                    bookItemImage = bookItemImage,
                    onCameraClick = onCameraClick,
                    onFavoriteClick = onFavoriteClick,
                )
            } ?: BookInfoImage(
                book = book,
                bookItemImage = ImageBitmap.imageResource(id = android.R.drawable.ic_menu_gallery),
                onCameraClick = onCameraClick,
                onFavoriteClick = onFavoriteClick,
            )

            BookInfoDetail(
                book = book,
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailTopAppbar(onBack: () -> Unit, onSelection: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(BookReportIcons.ArrowBackIosNew, contentDescription = stringResource(R.string.description_go_back))
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

@Composable
fun BookInfoImage(
    book: Book,
    bookItemImage: ImageBitmap,
    onCameraClick: () -> Unit,
    onFavoriteClick: (Book) -> Unit,
    isFavorite: Boolean = false,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(312.dp)
    ) {
        Image(
            bitmap = bookItemImage,
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
        bookItemImage = ImageBitmap.imageResource(id = android.R.drawable.ic_menu_gallery),
        onCameraClick = {},
        onFavoriteClick = {},
        isFavorite = false,
        book = emptyBook
    )
}

@Preview(showBackground = true)
@Composable
fun BookInfoDetailScreenPreview(modifier: Modifier = Modifier) {
}

package com.twitty.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.emptyBook


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookCard(book: Book, modifier: Modifier = Modifier, onClick: () -> Unit) {

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(CornerSize(8.dp)),
        modifier = modifier
            .fillMaxWidth()
            .height(166.dp)
    ) {
        Row {
            GlideImage(
                model = book.image,
                contentDescription = null,
                modifier = Modifier.size(166.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                Text(book.title)
                Text(book.author)
                Text(book.discount)
                Text(book.publisher)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookReportCard(
    bookReport: BookReport,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(CornerSize(8.dp)),
        modifier = modifier
            .fillMaxWidth()
            .height(166.dp)
    ) {
        Row {
            GlideImage(
                model = bookReport.book.image,
                contentDescription = null,
                modifier = Modifier.size(166.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                Text(bookReport.title)
                Text(bookReport.content)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookCardPreview(modifier: Modifier = Modifier) {
    BookCard(
        book = emptyBook,
        modifier = modifier,
        onClick = {}
    )
}
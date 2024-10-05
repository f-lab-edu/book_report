package com.towitty.bookreport.ui.bookreport

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.towitty.bookreport.R

@Composable
fun BookReportScreen(onCancel: () -> Unit, onSave: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            BookReportTopAppbar(onCancel, onSave)
        },
        modifier = modifier
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            BookReportBookInfo(
                bookCover = painterResource(id = R.drawable.ic_launcher_foreground),
                bookTitle = "제목",
                genre = "장르",
                content = "소개",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(4.dp))
            BookReportContent(
                Modifier.fillMaxWidth()
            )
        }
        

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BookReportTopAppbar(onCancel: () -> Unit, onSave: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {},
        navigationIcon = {
            TextButton(
                onClick = onCancel,
                modifier = Modifier
                    .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            ) {
                Text(stringResource(R.string.str_cancel))
            }
        },
        actions = {
            TextButton(
                onClick = onSave,
                modifier = Modifier
                    .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            ) {
                Text(stringResource(R.string.str_save))
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun BookReportBookInfo(
    bookCover: Painter,
    bookTitle: String,
    genre: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "책 정보",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )
        Box(modifier = Modifier.height(190.dp)) {
            Row(modifier = Modifier.height(166.dp)) {
                Image(
                    painter = bookCover,
                    contentDescription = "Book Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(166.dp)
                        .padding(end = 8.dp)
                )
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(166.dp)
                ) {
                    Text(
                        text = bookTitle,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = content,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.align(alignment = Alignment.BottomEnd)
            ) {
                Text(text = stringResource(id = R.string.str_edit))
            }
        }
    }

}

@Composable
fun BookReportContent(modifier: Modifier = Modifier) {
    var bookReportTitle by rememberSaveable {
        mutableStateOf("")
    }
    var bookReportContent by rememberSaveable {
        mutableStateOf("")
    }
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = bookReportTitle,
            onValueChange = { bookReportTitle = it },
            placeholder = { Text(text = stringResource(R.string.placeholer_bookreport_title)) },
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = bookReportContent,
            onValueChange = { bookReportContent = it },
            placeholder = { Text(text = stringResource(R.string.placeholer_bookreport_content)) },
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
        )
        Text(text = "마지막 수정 0000-00-00", modifier = Modifier.align(Alignment.End))
    }

}

@Preview(showBackground = true)
@Composable
fun BookReportContentPreview(modifier: Modifier = Modifier) {
    BookReportContent()
}

@Preview(showBackground = true)
@Composable
fun BookReportBookInfoPreview(modifier: Modifier = Modifier) {
    BookReportBookInfo(
        bookCover = painterResource(id = R.drawable.ic_launcher_foreground),
        bookTitle = "첵 제목",
        genre = "장르",
        content = "책 소개"
    )
}

@Preview(showBackground = true)
@Composable
fun BookReportScreenPreview(modifier: Modifier = Modifier) {
    BookReportScreen(onCancel = {}, onSave = {}, modifier.fillMaxSize())
}
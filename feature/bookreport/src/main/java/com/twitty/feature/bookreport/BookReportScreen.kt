package com.twitty.feature.bookreport

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.twitty.core.data.util.getCurrentDateTime
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.emptyBookReport
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.serialization.json.JsonNull.content

@OptIn(FlowPreview::class)
@Composable
fun BookReportScreen(
    onNavigateUp: () -> Unit,
    onAddSelectedTag: (Int) -> Unit = { 0 },
    onRemoveTag: (Int) -> Unit = {},
    onNavigateAddTag: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: BookReportViewModel = hiltViewModel()
) {
    val bookReport by viewModel.bookReport.collectAsStateWithLifecycle()
    var bookReportTitleState by rememberSaveable { mutableStateOf(bookReport.title) }
    var bookReportContentState by rememberSaveable { mutableStateOf(bookReport.content) }

    val onChangeBookReportTitle: (String) -> Unit = { bookReportTitleState = it }
    val onChangeBookReportContent: (String) -> Unit = { bookReportContentState = it }

    LaunchedEffect(
        key1 = bookReportTitleState,
        key2 = bookReportContentState
    ) {
        snapshotFlow { Pair(bookReportTitleState, bookReportContentState) }
            .debounce(300)
            .collect { (title, content) ->
                viewModel.updateTitleAndContent(title = title, content = content)
            }
    }

    Scaffold(
        topBar = {
            BookReportTopAppbar(
                onCancel = onNavigateUp,
                onSaveBookReport = viewModel::saveBookReport
            )
        },
        modifier = modifier
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            BookReportBookInfo(
                book = bookReport.book,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(4.dp))
            BookReportContent(
                bookReport = bookReport,
                bookReportTitleState = bookReportTitleState,
                bookReportContentState = bookReportContentState,
                onBookReportTitleChange = onChangeBookReportTitle,
                onBookReportContentChange = onChangeBookReportContent,
                onAddSelectedTag = onAddSelectedTag,
                onRemoveTag = onRemoveTag,
                onNavigateAddTag = onNavigateAddTag,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BookReportTopAppbar(
    onCancel: () -> Unit,
    onSaveBookReport: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                onClick = onSaveBookReport,
                modifier = Modifier
                    .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            ) {
                Text(stringResource(R.string.str_save))
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookReportBookInfo(
    book: Book,
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
                GlideImage(
                    model = book.image,
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
                        text = book.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = book.author,
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
fun BookReportContent(
    bookReport: BookReport,
    bookReportTitleState: String,
    bookReportContentState: String,
    onBookReportTitleChange: (String) -> Unit,
    onBookReportContentChange: (String) -> Unit,
    onAddSelectedTag: (Int) -> Unit,
    onRemoveTag: (Int) -> Unit,
    onNavigateAddTag: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = if(bookReport.title != bookReportTitleState) bookReportTitleState else bookReport.title,
            onValueChange = { onBookReportTitleChange(it) },
            placeholder = { Text(text = stringResource(R.string.placeholer_bookreport_title)) },
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(8.dp))
        TagListWithAddButton(
            onNavigateAddTag,
            onAddSelectedTag,
            onRemoveTag,
            bookReport = bookReport,
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = if(bookReport.content != bookReportContentState) bookReportContentState else bookReport.content,
            onValueChange = { onBookReportContentChange(it) },
            placeholder = { Text(text = stringResource(R.string.placeholer_bookreport_content)) },
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
        )
        Text(
            text = "마지막 수정 ${bookReport.date.ifEmpty { getCurrentDateTime() }}",
            modifier = Modifier.align(Alignment.End)
        )
    }

}

@Composable
fun TagListWithAddButton(
    onNavigateAddTag: () -> Unit,
    onAddSelectedTag: (Int) -> Unit,
    onRemoveTag: (Int) -> Unit,
    bookReport: BookReport = emptyBookReport,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        ) {
            items(bookReport.tags) { tag ->
                if (tag.id != 0) {
                    TagItem(
                        tag = tag,
                        icon = R.drawable.ic_outline_cancel,
                        onClicked = onAddSelectedTag,
                        onRemoveTag = onRemoveTag
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_rounded_add_circle),
            contentDescription = stringResource(R.string.description_add_tag),
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterVertically)
                .clickable { onNavigateAddTag() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookReportContentPreview(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun BookReportBookInfoPreview(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun BookReportScreenPreview(modifier: Modifier = Modifier) {
}
package com.towitty.bookreport.presentation.ui.screens.bookreport

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.towitty.bookreport.R
import com.towitty.bookreport.data.repository.model.Tag
import com.towitty.bookreport.presentation.ui.common.BookReportIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTagScreen(
    onNavigateUp: () -> Unit,
    onAddSelectedTag: (Int) -> Unit,
    onRemoveTag: (Int) -> Unit,
    tagListState: State<List<Tag>>
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("태그 추가") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            BookReportIcons.ArrowBackIosNew,
                            contentDescription = stringResource(id = R.string.description_go_back)
                        )
                    }
                }
            )
        }

    ) { paddingValues ->
        val tagList by tagListState

        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(tagList) { tag ->
                TagItem(
                    tag = tag,
                    onClicked = onAddSelectedTag,
                    onRemoveTag = onRemoveTag
                )
            }
        }
    }
}

@Composable
fun TagItem(
    tag: Tag,
    icon: Int = -1,
    onClicked: (Int) -> Unit,
    onRemoveTag: (Int) -> Unit
) {
    val tagColor = Color(tag.color)

    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(tagColor)
            .also {
                if (icon == -1) {
                    it.clickable {
                        onClicked(tag.id)
                    }
                }
            }
    ) {
        Row {
            TextButton(
                onClick = { onClicked(tag.id) },
            ) {
                Text(tag.name, color = Color.White)
            }
            if (icon != -1) {
                IconButton(
                    onClick = { onRemoveTag(tag.id) },
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = stringResource(R.string.description_delete_tag)
                    )
                }
            }
        }
    }
}

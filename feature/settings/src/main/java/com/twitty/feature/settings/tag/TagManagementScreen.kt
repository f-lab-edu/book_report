package com.twitty.feature.settings.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.twitty.core.ui.TagModal
import com.twitty.designsystem.icon.BookReportIcons
import com.twitty.feature.settings.R
import com.twitty.model.Tag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagManagementScreen(
    onNavigateUp: () -> Unit,
    viewModel: TagViewModel = hiltViewModel()
) {
    val tags by viewModel.tags.collectAsState()
    var isShowTagSheet by remember { mutableStateOf(false) }

    Surface {
        Column {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.tag_management)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            BookReportIcons.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.description_go_back)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            TagSlot(
                text = stringResource(id = R.string.str_custom),
                onShowTagSheet = { isShowTagSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                TagItems(tags)
            }
            if (isShowTagSheet) {
                TagModal {
                    isShowTagSheet = false
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagItems(tags: List<Tag>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            Text(
                text = tag.name,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .background(Color(tag.color), shape = RoundedCornerShape(4.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun TagSlot(
    text: String,
    modifier: Modifier = Modifier,
    onShowTagSheet: () -> Unit,
    content: @Composable () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        val (title, icon) = createRefs()
        Text(
            text,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .width(200.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .padding(8.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        IconButton(
            onClick = onShowTagSheet,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = BookReportIcons.AddCircleOutline,
                contentDescription = stringResource(id = R.string.description_add_tag),
                tint = Color.Black,
            )
        }
    }
    content()
}
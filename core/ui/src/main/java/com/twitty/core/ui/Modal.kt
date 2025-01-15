package com.twitty.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.twitty.designsystem.icon.BookReportIcons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FabModal(
    onNavigateToBookSearch: () -> Unit,
    onNavigateToBookReport: () -> Unit,
    onNavigateToBarcode: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            FabModalSheetItem(
                icon = BookReportIcons.Keyboard,
                label = stringResource(R.string.fab_modal_keyboard),
                onClicked = {
                    onDismissRequest()
                    onNavigateToBookReport()
                },
            )
            FabModalSheetItem(
                icon = BookReportIcons.Search,
                label = stringResource(R.string.fab_modal_book_search),
                onClicked = {
                    onDismissRequest()
                    onNavigateToBookSearch()
                }
            )
            FabModalSheetItem(
                icon = BookReportIcons.Scanner,
                label = stringResource(R.string.fab_modal_barcode_scanner),
                onClicked = {
                    onDismissRequest()
                    onNavigateToBarcode()
                }
            )
        }

    }
}

@Composable
fun FabModalSheetItem(icon: ImageVector, label: String, onClicked: () -> Unit) {
    TextButton(onClick = onClicked, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagModal(
    colors: List<Color>,
    selectedColor: Color,
    onSelectedColor: (Color) -> Unit,
    onCreateTag: (String, Color) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var tagName by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(colors) { color ->
                Box(
                    modifier = Modifier
                        .clickable { onSelectedColor(color) }
                        .background(color.copy(alpha = 0.8f), shape = CircleShape)
                        .size(48.dp)
                        .drawBehind {
                            if (selectedColor == color) {
                                drawCircle(
                                    color = color.copy(alpha = 0.3f),
                                    radius = size.minDimension / 2,
                                    center = Offset(size.width / 2, size.height / 2),
                                    style = Stroke(width = 8.dp.toPx())
                                )
                            }
                        }
                )
            }
        }
        OutlinedTextField(
            value = tagName,
            onValueChange = { tagName = it },
            label = { Text(text = stringResource(id = R.string.tag_name)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (cancelBtn, addBtn) = createRefs()
            Button(
                onClick = onDismissRequest,
                modifier = Modifier.constrainAs(cancelBtn) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            ) {
                Text(text = stringResource(id = R.string.str_cancel))
            }
            Button(
                onClick = { onCreateTag(tagName, selectedColor) },
                modifier = Modifier.constrainAs(addBtn) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
            ) {
                Text(text = stringResource(id = R.string.str_registration))
            }
        }
    }
}
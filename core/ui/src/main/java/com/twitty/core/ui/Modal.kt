package com.twitty.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twitty.designsystem.icon.BookReportIcons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FabModal(
    onNavigateRoute: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
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
                    onNavigateRoute()
                },
            )
            FabModalSheetItem(
                icon = BookReportIcons.Search,
                label = stringResource(R.string.fab_modal_book_search),
                onClicked = {
                    onDismissRequest()
                    onNavigateRoute()
                }
            )
            FabModalSheetItem(
                icon = BookReportIcons.Scanner,
                label = stringResource(R.string.fab_modal_barcode_scanner),
                onClicked = {/* 바코드 스크린 이동 */ }
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
package com.towitty.bookreport.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.towitty.bookreport.R
import com.towitty.bookreport.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FabModal(
    onClicked: (String) -> Unit,
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
                icon = Icons.Default.Keyboard,
                label = stringResource(R.string.fab_modal_keyboard),
                onClicked = {
                    onDismissRequest()
                    onClicked(Routes.DIRECTLY_BOOK_REPORT)
                },
            )
            FabModalSheetItem(
                icon = Icons.Default.Search,
                label = stringResource(R.string.fab_modal_book_search),
                onClicked = {
                    onDismissRequest()
                    onClicked(Routes.BOOK_SEARCH_FOR_BOOK_REPORT)
                }
            )
            FabModalSheetItem(
                icon = ImageVector.vectorResource(id = R.drawable.ic_barcode_scanner),
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
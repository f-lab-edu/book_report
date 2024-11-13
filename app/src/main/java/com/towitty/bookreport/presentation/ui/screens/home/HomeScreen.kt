package com.towitty.bookreport.presentation.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.towitty.bookreport.R
import com.towitty.bookreport.presentation.ui.common.BookReportIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    moveSettings: (context: Context) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        Column {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = { moveSettings(context) },
                        content = { Icon(BookReportIcons.Settings, contentDescription = null) }
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
}
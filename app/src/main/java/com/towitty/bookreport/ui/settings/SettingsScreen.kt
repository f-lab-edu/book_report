package com.towitty.bookreport.ui.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.towitty.bookreport.R

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Text(text = stringResource(id = R.string.label_settings),modifier = modifier.fillMaxSize())
}
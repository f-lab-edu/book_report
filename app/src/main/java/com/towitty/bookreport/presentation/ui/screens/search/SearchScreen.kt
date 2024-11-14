package com.towitty.bookreport.presentation.ui.screens.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.towitty.bookreport.R

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Text(text = stringResource(id = R.string.label_search),modifier = modifier.fillMaxSize())
}
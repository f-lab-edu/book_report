package com.towitty.bookreport.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.towitty.bookreport.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Surface {

    }
    Text(text = stringResource(id = R.string.label_home),modifier = modifier.fillMaxSize())

}
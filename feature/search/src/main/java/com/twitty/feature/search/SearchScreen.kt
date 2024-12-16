package com.twitty.feature.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Surface {
        Text(
            text = stringResource(id = R.string.label_search),
            modifier = modifier.fillMaxSize()
        )
    }

}
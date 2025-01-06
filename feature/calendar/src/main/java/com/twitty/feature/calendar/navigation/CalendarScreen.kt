package com.twitty.feature.calendar.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource


@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    Surface {
        Text(
            text = stringResource(id = com.twitty.core.ui.R.string.label_calender),
            modifier = modifier.fillMaxSize()
        )
    }
}
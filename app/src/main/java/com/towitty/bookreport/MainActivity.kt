package com.towitty.bookreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.towitty.bookreport.ui.BookReportApp
import com.towitty.bookreport.ui.rememberBookReportAppState
import com.twitty.designsystem.theme.BookReportTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberBookReportAppState()

            BookReportTheme {
                BookReportApp(appState)
            }
        }
    }
}


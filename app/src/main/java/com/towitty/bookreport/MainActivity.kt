package com.towitty.bookreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.towitty.bookreport.ui.BookReportApp
import com.towitty.bookreport.ui.theme.BookReportTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookReportTheme {
                BookReportApp()
            }
        }
    }
}
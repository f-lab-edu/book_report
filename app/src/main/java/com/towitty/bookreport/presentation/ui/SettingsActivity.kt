package com.towitty.bookreport.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.towitty.bookreport.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
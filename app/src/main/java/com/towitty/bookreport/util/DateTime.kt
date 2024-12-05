package com.towitty.bookreport.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val date = Calendar.getInstance().time
    return dateFormat.format(date)
}
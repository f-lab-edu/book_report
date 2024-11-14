package com.towitty.bookreport.presentation.model

import com.towitty.bookreport.R

enum class ColorItem(val color: Int, var selected: Boolean = false) {
    RED(R.color.red, true),
    ORANGE(R.color.orange),
    YELLOW(R.color.yellow),
    GREEN(R.color.green),
    BLUE(R.color.blue),
    INDIGO(R.color.indigo),
    VIOLET(R.color.violet)
}
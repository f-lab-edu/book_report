package com.twitty.model

data class ColorPicker(
    val color: Int,
    val selected: Boolean = false
) {
    fun toggle() = copy(selected = !selected)
}
package com.towitty.bookreport.presentation.ui.common

interface IconState {
    var isList: Boolean
    var isCard: Boolean
}

data class FavoriteIconState(
    override var isList: Boolean = false,
    override var isCard: Boolean = true,
) : IconState

data class BookReportIconState(
    override var isList: Boolean = true,
    override var isCard: Boolean = false,
) : IconState

package com.twitty.model

import kotlinx.serialization.Serializable

@Serializable
data class BookSearchCriteria(
    val title: String? = null,
    val isbn: String? = null,
    val id: Long? = null,
)

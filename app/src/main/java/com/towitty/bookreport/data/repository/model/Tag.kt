package com.towitty.bookreport.data.repository.model

import com.towitty.bookreport.data.database.model.TagEntity

data class Tag(
    val id: Int,
    val name: String,
    val color: Int
)

fun Tag.asEntity(): TagEntity {
    return TagEntity(
        id = id,
        name = name,
        color = color
    )
}
package com.towitty.bookreport.data.database.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.towitty.bookreport.data.repository.model.Tag

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val color: Int = Color.White.value.toInt()
)

fun TagEntity.asTag(): Tag = Tag(id = id, name = name, color = color)

val emptyTagEntity = TagEntity(0, "empty", Color.White.value.toInt())

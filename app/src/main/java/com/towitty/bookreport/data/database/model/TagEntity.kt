package com.towitty.bookreport.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.towitty.bookreport.data.repository.model.Tag

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val color: Int
)

fun TagEntity.asTag(): Tag = Tag(id = id, name = name, color = color)

val emptyTagEntity = TagEntity(0, "", 0)

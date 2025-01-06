package com.twitty.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

val emptyTagEntity = TagEntity(0, "empty", 0)

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val color: Int = 0
)

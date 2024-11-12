package com.towitty.bookreport.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val color: Int
)

val emptyTagEntity = TagEntity(0, "", 0)

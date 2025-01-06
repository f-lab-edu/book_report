package com.twitty.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val isbn: String,
    val title: String,
    val author: String,
    val link: String,
    val image: String,
    val description: String,
    val price: String,
    val publisher: String,
    val pubDate: String,
    val isFavorite: Boolean = false,
)

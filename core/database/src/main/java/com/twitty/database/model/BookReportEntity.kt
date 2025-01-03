package com.twitty.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_reports")
data class BookReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val date: String,
    val isFavorite: Boolean,
    val bookId: Long,
    val tagIds: List<Int>
)

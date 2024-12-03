package com.towitty.bookreport.data.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class BookReportWithTags(
    @Embedded val bookReport: BookReportEntity,
    @Relation(
        parentColumn = "bookReportId",
        entityColumn = "tagId"
    )
    val tags: List<TagEntity>
)

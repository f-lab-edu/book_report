package com.towitty.bookreport.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.towitty.bookreport.data.database.model.BookEntity
import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.database.model.TagEntity


@Database(
    entities = [
        TagEntity::class,
        BookReportEntity::class,
        BookEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(TagIdsConverter::class)
abstract class BookReportDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun bookReportDao(): BookReportDao

    abstract fun tagDao(): TagDao
}
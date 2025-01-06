package com.twitty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.twitty.database.dao.BookDao
import com.twitty.database.dao.BookReportDao
import com.twitty.database.dao.TagDao
import com.twitty.database.util.TagIdsConverter


@Database(
    entities = [
        com.twitty.database.model.TagEntity::class,
        com.twitty.database.model.BookReportEntity::class,
        com.twitty.database.model.BookEntity::class
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
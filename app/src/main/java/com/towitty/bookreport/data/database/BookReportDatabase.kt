package com.towitty.bookreport.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.towitty.bookreport.data.database.model.TagEntity


@Database(entities = [TagEntity::class], version = 1, exportSchema = false)
abstract class BookReportDatabase : RoomDatabase() {

    abstract fun tagDao(): TagDao
}
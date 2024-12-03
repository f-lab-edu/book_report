package com.towitty.bookreport.data.database

import androidx.room.TypeConverter

class TagIdsConverter {

    @TypeConverter
    fun fromTagIds(tagIds: List<Int>): String = tagIds.joinToString(",")

    @TypeConverter
    fun toTagIds(tagIdsString: String): List<Int> = tagIdsString.split(",").map { it.toInt() }
}
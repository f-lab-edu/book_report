package com.twitty.database.util

import androidx.room.TypeConverter

class TagIdsConverter {

    @TypeConverter
    fun fromTagIds(tagIds: List<Int>): String = tagIds.joinToString(",")

    @TypeConverter
    fun toTagIds(tagIdsString: String): List<Int> = tagIdsString.ifEmpty { "0" }.split(",").map { it.toInt() }
}
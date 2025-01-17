package com.twitty.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.twitty.database.model.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert
    suspend fun insertTag(tagEntity: TagEntity)

    @Update
    suspend fun updateTag(tagEntity: TagEntity)

    @Delete
    suspend fun deleteTag(tagEntity: TagEntity)

    @Query("SELECT * FROM tags WHERE id = :id LIMIT 1")
    suspend fun fetchTag(id: Int): List<TagEntity>

    @Query("SELECT * FROM tags ORDER BY name ASC")
    fun fetchAllTags(): Flow<List<TagEntity>>

}
package com.towitty.bookreport.data.local

import com.towitty.bookreport.model.TagEntity
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun insertTag(tagEntity: TagEntity)
    suspend fun updateTag(tagEntity: TagEntity)
    suspend fun deleteTag(tagEntity: TagEntity)
    fun getTag(id: Int): Flow<TagEntity>
    fun getAllTags(): Flow<List<TagEntity>>
}
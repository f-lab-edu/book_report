package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.model.TagEntity
import kotlinx.coroutines.flow.Flow

interface ITagRepository {
    suspend fun insertTag(tagEntity: TagEntity)
    suspend fun updateTag(tagEntity: TagEntity)
    suspend fun deleteTag(tagEntity: TagEntity)
    suspend fun getTag(id: Int): TagEntity
    fun getAllTags(): Flow<List<TagEntity>>
}
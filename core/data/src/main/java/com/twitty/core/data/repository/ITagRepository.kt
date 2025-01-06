package com.twitty.core.data.repository

import com.twitty.model.Tag
import kotlinx.coroutines.flow.Flow

interface ITagRepository {
    suspend fun insertTag(tag: Tag)
    suspend fun updateTag(tag: Tag)
    suspend fun deleteTag(tag: Tag)
    suspend fun getTag(id: Int): Tag
    fun getAllTags(): Flow<List<Tag>>
}
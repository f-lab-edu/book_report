package com.twitty.core.data.repository

import com.twitty.model.Tag
import kotlinx.coroutines.flow.Flow

interface ITagRepository {
    suspend fun insertTag(name: String, color: Int)
    suspend fun deleteTag(tag: Tag)
    suspend fun fetchTag(id: Int): Tag
    fun fetchAllTags(): Flow<List<Tag>>
}
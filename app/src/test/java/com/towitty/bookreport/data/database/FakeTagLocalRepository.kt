package com.towitty.bookreport.data.database

import com.twitty.core.data.repository.ITagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTagLocalRepository(
    private val tagDatabase: MutableList<com.twitty.database.model.TagEntity>
) : ITagRepository {

    override suspend fun insertTag(tagEntity: com.twitty.database.model.TagEntity) {
        tagDatabase.add(tagEntity)
    }

    override suspend fun updateTag(tagEntity: com.twitty.database.model.TagEntity) {
        val index = tagDatabase.indexOfFirst { it.id == tagEntity.id }
        if (index >= 0) {
            tagDatabase[index] = tagEntity
        }
    }

    override suspend fun deleteTag(tagEntity: com.twitty.database.model.TagEntity) {
        tagDatabase.remove(tagEntity)
    }

    override suspend fun fetchTag(id: Int): com.twitty.database.model.TagEntity {
        return tagDatabase.find { it.id == id } ?: com.twitty.database.model.emptyTagEntity
    }

    override fun fetchAllTags(): Flow<List<com.twitty.database.model.TagEntity>> = flow {
        tagDatabase
    }

}
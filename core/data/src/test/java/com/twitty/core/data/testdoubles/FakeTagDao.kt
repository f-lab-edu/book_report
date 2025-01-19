package com.twitty.core.data.testdoubles

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTagDao(
    val tagDatabase: MutableList<com.twitty.database.model.TagEntity>
) : com.twitty.database.dao.TagDao {

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

    override suspend fun fetchTag(id: Int): List<com.twitty.database.model.TagEntity> =
        tagDatabase.filter { it.id == id }

    override fun fetchAllTags(): Flow<List<com.twitty.database.model.TagEntity>> = flow {
        emit(tagDatabase.toList())
    }

}
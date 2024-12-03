package com.towitty.bookreport.data.database

import com.towitty.bookreport.data.database.model.TagEntity
import com.towitty.bookreport.data.database.model.emptyTagEntity
import com.towitty.bookreport.data.repository.ITagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTagLocalRepository(
    private val tagDatabase: MutableList<TagEntity>
) : ITagRepository {

    override suspend fun insertTag(tagEntity: TagEntity) {
        tagDatabase.add(tagEntity)
    }

    override suspend fun updateTag(tagEntity: TagEntity) {
        val index = tagDatabase.indexOfFirst { it.id == tagEntity.id }
        if (index >= 0) {
            tagDatabase[index] = tagEntity
        }
    }

    override suspend fun deleteTag(tagEntity: TagEntity) {
        tagDatabase.remove(tagEntity)
    }

    override suspend fun getTag(id: Int): TagEntity {
        return tagDatabase.find { it.id == id } ?: emptyTagEntity
    }

    override fun getAllTags(): Flow<List<TagEntity>> = flow {
        tagDatabase
    }

}
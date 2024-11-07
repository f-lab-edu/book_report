package com.towitty.bookreport.data.local

import com.towitty.bookreport.model.TagEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTagLocalRepository(
    private val tagDatabase: MutableList<TagEntity>
) : TagRepository {

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

    override fun getTag(id: Int): Flow<TagEntity> = flow {
        tagDatabase.first { it.id == id }
    }

    override fun getAllTags(): Flow<List<TagEntity>> = flow {
        tagDatabase
    }

}
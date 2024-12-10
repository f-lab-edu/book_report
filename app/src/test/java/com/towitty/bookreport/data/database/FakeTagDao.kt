package com.towitty.bookreport.data.database

import com.towitty.bookreport.data.database.model.TagEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTagDao(
    val tagDatabase: MutableList<TagEntity>
) : TagDao {

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

    override suspend fun getTag(id: Int): List<TagEntity> =
        tagDatabase.filter { it.id == id }

    override fun getAllTags(): Flow<List<TagEntity>> = flow {
        emit(tagDatabase.toList())
    }

}
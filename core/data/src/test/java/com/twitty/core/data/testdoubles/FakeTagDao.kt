package com.twitty.core.data.testdoubles

import com.twitty.database.dao.TagDao
import com.twitty.database.model.TagEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeTagDao : TagDao {

    val tagEntities = MutableStateFlow<List<TagEntity>>(emptyList())

    override suspend fun insertTag(tagEntity: TagEntity) {
        tagEntities.update { oldValues ->
            oldValues.map {
                if (it.id == tagEntity.id) {
                    return
                }
            }
            oldValues + tagEntity
        }
    }

    override suspend fun updateTag(tagEntity: TagEntity) {
        tagEntities.update { tags ->
            tags.map { tag ->
                if (tag.id == tagEntity.id) {
                    tagEntity
                } else {
                    tag
                }
            }
        }
    }

    override suspend fun deleteTag(tagEntity: TagEntity) {
        tagEntities.update { tags ->
            tags.filterNot { it.id == tagEntity.id }
        }
    }

    override suspend fun fetchTag(id: Int): List<TagEntity> =
        tagEntities.value.filter { it.id == id }

    override fun fetchAllTags(): Flow<List<TagEntity>> = tagEntities

}
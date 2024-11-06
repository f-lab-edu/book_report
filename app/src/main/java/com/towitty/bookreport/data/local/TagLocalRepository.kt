package com.towitty.bookreport.data.local

import com.towitty.bookreport.model.TagEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TagLocalRepository @Inject constructor(private val tagDao: TagDao) : TagRepository {

    override suspend fun insertTag(tagEntity: TagEntity) = tagDao.insertTag(tagEntity)

    override suspend fun updateTag(tagEntity: TagEntity) = tagDao.updateTag(tagEntity)

    override suspend fun deleteTag(tagEntity: TagEntity) = tagDao.deleteTag(tagEntity)

    override fun getTag(id: Int): Flow<TagEntity> = tagDao.getTag(id)

    override fun getAllTags(): Flow<List<TagEntity>> = tagDao.getAllTags()
}
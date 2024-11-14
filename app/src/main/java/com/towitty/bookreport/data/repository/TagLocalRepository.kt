package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.TagDao
import com.towitty.bookreport.data.database.model.TagEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TagLocalRepository @Inject constructor(private val tagDao: TagDao) : ITagRepository {

    override suspend fun insertTag(tagEntity: TagEntity) = tagDao.insertTag(tagEntity)

    override suspend fun updateTag(tagEntity: TagEntity) = tagDao.updateTag(tagEntity)

    override suspend fun deleteTag(tagEntity: TagEntity) = tagDao.deleteTag(tagEntity)

    override fun getTag(id: Int): Flow<TagEntity> = tagDao.getTag(id)

    override fun getAllTags(): Flow<List<TagEntity>> = tagDao.getAllTags()
}